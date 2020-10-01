package com.platform.services.configurations;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Schemas {
    @Valid
    @NotNull
    private String requests = null;

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public String getRequests() {
        return requests;
    }
}
