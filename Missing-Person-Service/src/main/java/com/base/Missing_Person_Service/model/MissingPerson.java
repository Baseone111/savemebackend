package com.base.Missing_Person_Service.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "missing_person")
@Data

public class MissingPerson {
    @Id
    private String id;

    private String fullName;

    private String attachmentUrl;
    private Integer age;
    private LocalDateTime lastSeenDate;
    private String lastSeenLocation;
    private String relationship;
    private String description;

    @Override
    public String toString() {
        return "MissingPerson{" +
                "id=" + id +
                ", firstName='" + fullName + '\'' +

                ", photo='" + attachmentUrl+ '\'' +
                ", age=" + age +
                ", lastSeenDate=" + lastSeenDate +
                ", lastSeenLocation='" + lastSeenLocation + '\'' +
                ", relationship='" + relationship + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
