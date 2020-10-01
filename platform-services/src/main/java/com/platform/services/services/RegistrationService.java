package com.platform.services.services;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.platform.services.configurations.Configuration;
import com.platform.services.metadata.PlatformDevice;
import com.platform.services.metadata.PlatformUser;
import com.platform.services.platform.PlatformUserFunctions;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


/**
 * Author: mukthar.am@gmail.com (personal usage)
 * Date: 14-Jan-2020
 *
 * Usage: https://localhost:8443/pl-rest/welcome/status
 *
 */
@Slf4j
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationService {
    final static Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private Configuration configurations;
    private final Validator validator;
    private HealthCheckRegistry registry;

    public RegistrationService(Validator validator, Configuration configs, HealthCheckRegistry registry) {
        this.validator = validator;
        this.configurations = configs;
        this.registry = registry;
    }

    @GET
    @Path("/status")
    public Set<Map.Entry<String, HealthCheck.Result>> getStatus() {
        // return health check entry sets
        return registry.runHealthChecks().entrySet();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response connectionSanity(@Context UriInfo queryParams) throws IOException {
        String responseStr = "{\"message\": \"welcome to the platform services\"}";
        return Response.ok(responseStr).build();
    }


    /**
     *
     * @param user
     * @return
     */
    @POST
    @Path("/user")
    public Response registerUserLogin(PlatformUser user) {
        logger.info("user: " + user.toString());

        /** validations */
        Set<ConstraintViolation<PlatformUser>> violations = validator.validate(user);

        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<PlatformUser> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " +
                        violation.getMessage());
            }

            logger.error(
                    "Validation failures have occurred. Validation msgs: {}, incoming request: {}",
                    validationMessages, user.toString());

            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        } else {
            logger.info("all good...");

            /** register the user */
            user.registerUser();

            /** check for successful registration */
            if (PlatformUserFunctions.isKnownUser(user) != null) {
                logger.info("user found" );
            } else {
                logger.info("user NOT found...");
            }

            Response response = Response
                    .accepted("User registered successfully.")
                    .build();

            return response;

        }
    }


    /** ToDo: this is retained for explaination on the approaches. */
    @POST
    @Path("/device")
    public Response registerUserLogin(PlatformDevice device) {
        logger.info("device: " + device.toString());

        /** validations */
        Set<ConstraintViolation<PlatformDevice>> violations = validator.validate(device);

        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<PlatformDevice> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " +
                        violation.getMessage());
            }

            logger.error(
                    "Validation failures have occurred. Validation msgs: {}, incoming request: {}",
                    validationMessages, device.toString());

            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        } else {
            logger.info("all good...");

            /** register the user */
            device.registerDevice();

            /** register the device to the user */
            device.registerDeviceToUser();

            /** check for successful registration */
            if (PlatformUserFunctions.isKnownDevice(device) != null) {
                logger.info("Device found" );
            } else {
                logger.info("Device NOT found...");
            }

            Response response = Response
                    .accepted("device registered successfully.")
                    .build();

            return response;

        }
    }

}