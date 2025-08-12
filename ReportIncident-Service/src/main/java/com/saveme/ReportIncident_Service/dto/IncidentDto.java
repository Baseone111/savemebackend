package com.saveme.ReportIncident_Service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class IncidentDto {
    private String id;
    private String incidentType;
    private String description;
    private String location;
    private String urgency;
    private Boolean isAnonymous;
    private String attachmentUrl;
    private LocalDateTime reportedAt;

    // Default constructor
    public IncidentDto() {}

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }
    private IncidentDto incident;
    private List<IncidentDto> incidentList;

    // Add these getter and setter methods to your Response class
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
}