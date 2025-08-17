package com.base.Missing_Person_Service.controller;


import com.base.Missing_Person_Service.dto.Response;
import com.base.Missing_Person_Service.service.MissingPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/missingperson")
@CrossOrigin(origins = "*")
public class MissingPersonController {

    @Autowired
    private MissingPersonService missingPersonService;


    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/submit")
    public ResponseEntity<Response> submitMissingPerson(
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "lastSeenLocation", required = false) String lastSeenLocation,
            @RequestParam(value = "relationship", required = false) String relationship,
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "lastSeenDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd" ) String lastSeenDateStr,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment
    ) {
        LocalDateTime lastSeenDateTime = null;

        // Parse date string
        if (lastSeenDateStr != null && !lastSeenDateStr.trim().isEmpty()) {
            try {
                LocalDate parsedDate = LocalDate.parse(lastSeenDateStr.trim());
                lastSeenDateTime = parsedDate.atStartOfDay(); // Store parsed value
            } catch (Exception e) {
                Response response = new Response();
                response.setStatusCode(400);
                response.setMessage("Invalid date format. Please use yyyy-MM-dd format.");
                return ResponseEntity.status(response.getStatusCode()).body(response);
            }
        }

        System.out.println("Received parameters:");
        System.out.println("fullName: " + fullName);
        System.out.println("description: " + description);
        System.out.println("lastSeenLocation: " + lastSeenLocation);
        System.out.println("relationship: " + relationship);
        System.out.println("age: " + age);
        System.out.println("lastSeenDate: " + lastSeenDateStr);
        System.out.println("attachment: " + (attachment != null ? attachment.getOriginalFilename() : "null"));

        // Check for required fields
        if (fullName == null || fullName.isBlank() ||
                description == null || description.isBlank() ||
                lastSeenLocation == null || lastSeenLocation.isBlank() ||
                relationship == null || relationship.isBlank() ||
                lastSeenDateStr == null || lastSeenDateStr.isBlank()) {

            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all required fields (fullName, description, lastSeenLocation, relationship)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        // Convert to LocalDate if available
        LocalDate lastSeenDate = lastSeenDateTime != null ? lastSeenDateTime.toLocalDate() : null;

        Response response = missingPersonService.submitMissingPerson(
                fullName, age, attachment, lastSeenLocation, lastSeenDate, description, relationship
        );

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/all")
    public ResponseEntity<Response> getAllMissingPersons() {
        Response response = missingPersonService.getAllMissingPersons();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/relationships")
    public List<String> getAllRelationships() {
        return missingPersonService.getAllRelationships();
    }

    @GetMapping("/locations")
    public List<String> getAllLocations() {
        return missingPersonService.getAllLocations();
    }

    @GetMapping("/missing-person-by-id/{missingPersonId}")
    public ResponseEntity<Response> getMissingPersonById(@PathVariable("missingPersonId") String missingPersonId) {
        Response response = missingPersonService.getMissingPersonById(missingPersonId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-relationship-and-date-range")
    public ResponseEntity<Response> getMissingPersonsByRelationshipAndDateRange(
            @RequestParam(required = false) String relationship,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        if (relationship == null || relationship.isBlank() || startDate == null || endDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("All fields are required (relationship, startDate, endDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = missingPersonService.getMissingPersonByRelationshipAndDateRange(relationship, startDate, endDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{missingPersonId}")

    public ResponseEntity<Response> updateMissingPerson(
            @PathVariable String missingPersonId,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "lastSeenLocation", required = false) String lastSeenLocation,
            @RequestParam(value = "relationship", required = false) String relationship,
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "lastSeenDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate lastSeenDate,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment
    ) {

        Response response = missingPersonService.updateMissingPerson(
                missingPersonId, fullName, age, attachment, lastSeenLocation, lastSeenDate, description, relationship);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{missingPersonId}")

    public ResponseEntity<Response> deleteMissingPerson(@PathVariable String missingPersonId) {
        Response response = missingPersonService.deleteMissingPerson(missingPersonId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<Response> getRecentMissingPersons(@RequestParam(defaultValue = "10") int limit) {
        Response response = missingPersonService.getRecentMissingPersons(limit);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}