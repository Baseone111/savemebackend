package com.base.Resources_Service.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateResourceRequest {

    @NotBlank(message = "Title is required")
    @NotNull(message = "Title cannot be null")
    private String title;

    private String description;
    private String fileUrl;

    @NotNull(message = "Uploaded by user ID is required")
    private UUID uploadedBy;
}