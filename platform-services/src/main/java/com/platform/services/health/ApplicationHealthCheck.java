package com.platform.services.health;

import com.codahale.metrics.health.HealthCheck;

public class ApplicationHealthCheck extends HealthCheck {
    private final String template;

    public ApplicationHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() {
        /**
         * ToDo: We have to implement a CUSTOM application health check in such a way that we can pull all functional metrics
         * 1. total booking requests to begin with
         * 2. Total trip follow ups
         * 3. Input requests drops which did not reach thr trip planner admin
         * 4. Total trips which couldn't be assigned a driver
         */
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}