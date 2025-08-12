package com.base.Missing_Person_Service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MissingPersonDto {
    private String id;
    private String fullName;
    private String attachmentUrl;
    private Integer age;
    private LocalDateTime lastSeenDate;
    private String lastSeenLocation;
    private String relationship;
    private String description;

    public MissingPersonDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getLastSeenDate() {
        return lastSeenDate != null ? lastSeenDate.toLocalDate() : null;
    }

    public void setLastSeenDate(LocalDate lastSeenDate) {
        this.lastSeenDate = lastSeenDate != null ? lastSeenDate.atStartOfDay() : null;
    }

    public String getLastSeenLocation() {
        return lastSeenLocation;
    }

    public void setLastSeenLocation(String lastSeenLocation) {
        this.lastSeenLocation = lastSeenLocation;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}