package com.base.Alerts_Service.setup;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private String message;
    private String role;
    private String token;
    private String expirationTime;



    private Object data;

    // Incident fields


    // Alert fields
    private AlertDto alert;
    private List<AlertDto> alertList;



    // Alert getters and setters
    public AlertDto getAlert() {
        return alert;
    }

    public void setAlert(AlertDto alert) {
        this.alert = alert;
    }

    public List<AlertDto> getAlertList() {
        return alertList;
    }

    public void setAlertList(List<AlertDto> alertList) {
        this.alertList = alertList;
    }
}