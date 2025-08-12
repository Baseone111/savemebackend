package com.base.Resources_Service.base;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceMapper {

    public Resource toEntity(CreateResourceRequest request) {
        return Resource.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .fileUrl(request.getFileUrl())
                .uploadedBy(request.getUploadedBy())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ResourceDto toDto(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId())
                .title(resource.getTitle())
                .description(resource.getDescription())
                .fileUrl(resource.getFileUrl())
                .uploadedBy(resource.getUploadedBy())
                .createdAt(resource.getCreatedAt())
                .build();
    }

    public ResourceResponse toResponse(Resource resource) {
        return ResourceResponse.builder()
                .id(resource.getId())
                .title(resource.getTitle())
                .description(resource.getDescription())
                .fileUrl(resource.getFileUrl())
                .uploadedBy(resource.getUploadedBy())
                .createdAt(resource.getCreatedAt())
                .build();
    }

    public List<ResourceResponse> toResponseList(List<Resource> resources) {
        return resources.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(Resource resource, UpdateResourceRequest request) {
        if (request.getTitle() != null) {
            resource.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            resource.setDescription(request.getDescription());
        }
        if (request.getFileUrl() != null) {
            resource.setFileUrl(request.getFileUrl());
        }
    }
}