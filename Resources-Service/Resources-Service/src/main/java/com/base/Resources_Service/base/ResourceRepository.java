package com.base.Resources_Service.base;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String> {

    // Find resources by uploaded user
    List<Resource> findByUploadedBy(UUID uploadedBy);

    // Find resources by title containing (case-insensitive)
    @Query("{'title': {$regex: ?0, $options: 'i'}}")
    List<Resource> findByTitleContainingIgnoreCase(String title);

    // Find resources by file URL
    Optional<Resource> findByFileUrl(String fileUrl);

    // Check if resource exists by title
    boolean existsByTitle(String title);

    // Find resources ordered by creation date (newest first)
    List<Resource> findAllByOrderByCreatedAtDesc();

    // Find resources by user ordered by creation date
    List<Resource> findByUploadedByOrderByCreatedAtDesc(UUID uploadedBy);
}