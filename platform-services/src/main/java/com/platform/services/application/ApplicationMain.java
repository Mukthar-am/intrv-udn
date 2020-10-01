package com.platform.services.application;


import com.platform.services.auth.AppAuthorizer;
import com.platform.services.auth.AppBasicAuthenticator;
import com.platform.services.auth.User;
import com.platform.services.configurations.Configuration;
import com.platform.services.health.ApplicationHealthCheck;
import com.platform.services.services.UserService;
import io.dropwizard.Application;


import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import java.util.EnumSet;

/**
 * GCA application initializer
 *
 * Start the application by running the jar command as below;
 *
 * Reference links:
 *  # How to have a UI with angularJS - https://manifesto.co.uk/webcenter-sites-angularjs/
 *
 */
public class ApplicationMain extends Application<Configuration> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) throws Exception {
        new ApplicationMain().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }


    private void enableCorsHeaders(Environment env) {
        final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");


        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        logger.info("\nCalling run() method... NEW()\n");

        //Application health check
        final ApplicationHealthCheck healthCheck = new ApplicationHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        final UserService
                userService = new UserService(environment.getValidator(), configuration, environment.healthChecks());
        environment.jersey().register(userService);


        /****** Dropwizard security - custom classes ***********/
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new AppBasicAuthenticator())
                .setAuthorizer(new AppAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        enableCorsHeaders(environment);
    }
}

