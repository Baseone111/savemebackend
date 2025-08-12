package com.base.Resources_Service.base;

import java.util.List;
import java.util.UUID;

public interface ResourceService {

    ResourceResponse createResource(CreateResourceRequest request);

    ResourceResponse getResourceById(String id);

    List<ResourceResponse> getAllResources();

    List<ResourceResponse> getResourcesByUser(UUID uploadedBy);

    ResourceResponse updateResource(String id, UpdateResourceRequest request);

    void deleteResource(String id);

    List<ResourceResponse> searchResourcesByTitle(String title);

    boolean existsByTitle(String title);

    ResourceResponse getResourceByFileUrl(String fileUrl);
}