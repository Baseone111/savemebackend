package com.base.Resources_Service.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceDto {
    private String id;
    private String title;
    private String description;
    private String fileUrl;
    private UUID uploadedBy;
    private LocalDateTime createdAt;
}