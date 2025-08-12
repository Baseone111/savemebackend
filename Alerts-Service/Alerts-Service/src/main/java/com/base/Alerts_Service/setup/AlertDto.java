package com.base.Alerts_Service.setup;

import java.time.LocalDateTime;
import java.util.List;

public class AlertDto {
    private String id;
    private String title;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String createdBy;
    private String status;
    private String location;

    // Default constructor
    public AlertDto() {}

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Additional properties for Response class
    private AlertDto alert;
    private List<AlertDto> alertList;

    // Alert getters and setters for Response class
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