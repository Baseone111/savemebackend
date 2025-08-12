package com.saveme.ReportIncident_Service.dto;

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
//
//    private UserDto user;
//    private List<UserDto> userList;

    private Object data;

    // Incident fields
    private IncidentDto incident;
    private List<IncidentDto> incidentList;

    // Missing person fields
//    private MissingPersonDto missingPerson;
//    private List<MissingPersonDto> missingPersonList;

    // Alert fields
//    private AlertDto alert;
//    private List<AlertDto> alertList;

    // Incident getters and setters
    public IncidentDto getIncident() {
        return incident;
    }

    public void setIncident(IncidentDto incident) {
        this.incident = incident;
    }

    public List<IncidentDto> getIncidentList() {
        return incidentList;
    }

    public void setIncidentList(List<IncidentDto> incidentList) {
        this.incidentList = incidentList;
    }

    // Missing person getters and setters
//    public MissingPersonDto getMissingPerson() {
//        return missingPerson;
//    }
//
//    public void setMissingPerson(MissingPersonDto missingPerson) {
//        this.missingPerson = missingPerson;
//    }
//
//    public List<MissingPersonDto> getMissingPersonDtoList() {
//        return missingPersonList;
//    }
//
//    public void setMissingPersonDtoList(List<MissingPersonDto> missingPersonDtoList) {
//        this.missingPersonList = missingPersonDtoList;
//    }

    // Alert getters and setters
//    public AlertDto getAlert() {
//        return alert;
//    }
//
//    public void setAlert(AlertDto alert) {
//        this.alert = alert;
//    }
//
//    public List<AlertDto> getAlertList() {
//        return alertList;
//    }
//
//    public void setAlertList(List<AlertDto> alertList) {
//        this.alertList = alertList;
//    }
}