package com.base.Alerts_Service.setup;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "alerts")
public class Alert {
    @Id
    private String id;
    private String title;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String createdBy;
    private String status;
    private String location;

    @Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                ", sentAt=" + sentAt +
                ", createdBy='" + createdBy + '\'' +
                ", status='" + status + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}