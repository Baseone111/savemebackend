package com.saveme.ReportIncident_Service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "incident_reports")
public class IncidentReport {
    @Id
    private String id;
    private String incidentType;
    private String description;
    private String location;
    private String urgency;
    private Boolean isAnonymous;
    private String attachmentUrl;
    private LocalDateTime reportedAt;

    @Override
    public String toString() {
        return "IncidentReport{" +
                "id='" + id + '\'' +
                ", incidentType='" + incidentType + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", urgency='" + urgency + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", reportedAt=" + reportedAt +
                '}';
    }
}