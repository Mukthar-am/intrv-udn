package com.platform.services.configurations;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AppConfigurations {
    @Valid
    @NotNull
    private String sendMail = "false";

    @Valid
    @NotNull
    private String sendSMS = "false";

    @Valid
    @NotNull
    private String sendWhatsappSMS = "false";

    @Valid
    @NotNull
    private String enableCache = "false";

    public AppConfigurations() {
    }

    public void setSendMail(String sendMail) {
        this.sendMail = sendMail;
    }

    public boolean sendMail() {
        return Boolean.valueOf(this.sendMail);
    }

    public void setSendSMS(String sendSMS) {
        this.sendSMS = sendSMS;
    }

    public boolean sendSMS() {
        return Boolean.valueOf(this.sendSMS);
    }

    public void setSendWhatsappSMS(String sendWhatsappSMS) {
        this.sendWhatsappSMS = sendWhatsappSMS;
    }

    public boolean sendWhatsappSMS() {
        return Boolean.valueOf(this.sendWhatsappSMS);
    }

    public void setEnableCache(String enableCache) {
        this.enableCache = enableCache;
    }

    public boolean enableCaching() {
        return Boolean.valueOf(this.enableCache);
    }
}
