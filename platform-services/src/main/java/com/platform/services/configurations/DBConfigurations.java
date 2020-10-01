package com.platform.services.configurations;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DBConfigurations {
    /** Custom app based configurations */
    @NotNull
    @Valid
    public Schemas schemas;

    public Schemas getSchemas() { return schemas; }

    @Valid
    @NotNull
    private String poolSize = "2";

    @Valid
    @NotNull
    private String name = "gca";

    @Valid
    @NotNull
    private String host = "localhost";

    @Valid
    @NotNull
    private String user = "gca";

    @Valid
    @NotNull
    private String password = null;

    @Valid
    private String port = "5432";

    public void setName(String name) {
        this.name = name;
    }

    public String getDbName() {
        return name;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return this.port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDbHost() {
        return this.host;
    }

    public void setPoolSize(String size) {
        this.poolSize = size;
    }

    public int getPoolSize() {
        return Integer.parseInt(this.poolSize);
    }

    public String toString() {
        return String.format("DatabaseConfigurations: {DbName:%s, DbHost:%s, DBPort:%s, DBUser:%s, DBUser:%s, DbPoolSize: %s}"
                , getDbName(), getDbHost(), getPort(), getPassword(), getUser(), getPoolSize());
    }
}
