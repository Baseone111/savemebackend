package com.base.Resources_Service.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "resources")
public class Resource {

    @Id
    private String id;

    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("file_url")
    private String fileUrl;

    @Field("uploaded_by")
    private UUID uploadedBy;

    @Field("created_at")
    private LocalDateTime createdAt;

    // Pre-persist method to set creation timestamp
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}