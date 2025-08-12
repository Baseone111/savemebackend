package com.base.Resources_Service.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public ResourceResponse createResource(CreateResourceRequest request) {
        log.info("Creating new resource with title: {}", request.getTitle());

        Resource resource = resourceMapper.toEntity(request);
        Resource savedResource = resourceRepository.save(resource);

        log.info("Resource created successfully with ID: {}", savedResource.getId());
        return resourceMapper.toResponse(savedResource);
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceResponse getResourceById(String id) {
        log.info("Fetching resource with ID: {}", id);

        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));

        return resourceMapper.toResponse(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceResponse> getAllResources() {
        log.info("Fetching all resources");

        List<Resource> resources = resourceRepository.findAllByOrderByCreatedAtDesc();
        return resourceMapper.toResponseList(resources);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceResponse> getResourcesByUser(UUID uploadedBy) {
        log.info("Fetching resources for user: {}", uploadedBy);

        List<Resource> resources = resourceRepository.findByUploadedByOrderByCreatedAtDesc(uploadedBy);
        return resourceMapper.toResponseList(resources);
    }

    @Override
    public ResourceResponse updateResource(String id, UpdateResourceRequest request) {
        log.info("Updating resource with ID: {}", id);

        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + id));

        resourceMapper.updateEntityFromRequest(resource, request);
        Resource updatedResource = resourceRepository.save(resource);

        log.info("Resource updated successfully with ID: {}", updatedResource.getId());
        return resourceMapper.toResponse(updatedResource);
    }

    @Override
    public void deleteResource(String id) {
        log.info("Deleting resource with ID: {}", id);

        if (!resourceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found with ID: " + id);
        }

        resourceRepository.deleteById(id);
        log.info("Resource deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceResponse> searchResourcesByTitle(String title) {
        log.info("Searching resources by title: {}", title);

        List<Resource> resources = resourceRepository.findByTitleContainingIgnoreCase(title);
        return resourceMapper.toResponseList(resources);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTitle(String title) {
        return resourceRepository.existsByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceResponse getResourceByFileUrl(String fileUrl) {
        log.info("Fetching resource by file URL: {}", fileUrl);

        Resource resource = resourceRepository.findByFileUrl(fileUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with file URL: " + fileUrl));

        return resourceMapper.toResponse(resource);
    }
}
