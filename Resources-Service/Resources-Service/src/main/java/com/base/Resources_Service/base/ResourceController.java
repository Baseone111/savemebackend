package com.base.Resources_Service.base;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
@Validated
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResourceResponse>> createResource(
            @Valid @RequestBody CreateResourceRequest request) {

        ResourceResponse response = resourceService.createResource(request);
        ApiResponse<ResourceResponse> apiResponse = ApiResponse.success(response, "Resource created successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResourceResponse>> getResourceById(@PathVariable String id) {
        ResourceResponse response = resourceService.getResourceById(id);
        ApiResponse<ResourceResponse> apiResponse = ApiResponse.success(response, "Resource retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResourceResponse>>> getAllResources() {
        List<ResourceResponse> responses = resourceService.getAllResources();
        ApiResponse<List<ResourceResponse>> apiResponse = ApiResponse.success(responses, "Resources retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ResourceResponse>>> getResourcesByUser(@PathVariable UUID userId) {
        List<ResourceResponse> responses = resourceService.getResourcesByUser(userId);
        ApiResponse<List<ResourceResponse>> apiResponse = ApiResponse.success(responses, "User resources retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResourceResponse>> updateResource(
            @PathVariable String id,
            @Valid @RequestBody UpdateResourceRequest request) {

        ResourceResponse response = resourceService.updateResource(id, request);
        ApiResponse<ResourceResponse> apiResponse = ApiResponse.success(response, "Resource updated successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteResource(@PathVariable String id) {
        resourceService.deleteResource(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "Resource deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ResourceResponse>>> searchResourcesByTitle(
            @RequestParam String title) {

        List<ResourceResponse> responses = resourceService.searchResourcesByTitle(title);
        ApiResponse<List<ResourceResponse>> apiResponse = ApiResponse.success(responses, "Search completed successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/exists")
    public ResponseEntity<ApiResponse<Boolean>> checkResourceExists(@RequestParam String title) {
        boolean exists = resourceService.existsByTitle(title);
        ApiResponse<Boolean> apiResponse = ApiResponse.success(exists, "Resource existence check completed");

        return ResponseEntity.ok(apiResponse);
    }
}
