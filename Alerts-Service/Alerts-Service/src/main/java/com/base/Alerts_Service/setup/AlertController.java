package com.base.Alerts_Service.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/alert")
@CrossOrigin(origins = "*")
public class AlertController {

    @Autowired
    private IAlertService alertService;

    @PostMapping("/create")

    public ResponseEntity<Response> createAlert(@RequestBody Alert alert

    ) {
        if (alert.getTitle() == null || alert.getTitle().isBlank() ||
                alert.getMessage() == null || alert.getMessage().isBlank() ||
                alert.getType() == null || alert.getType().isBlank() ||
                alert.getCreatedBy() == null || alert.getCreatedBy().isBlank()) {

            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all required fields (title, message, type, createdBy)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        // You'll also need to update your alertService.createAlert method
        // to accept an Alert object instead of individual parameters.
        // For demonstration, let's assume it takes the object:
        Response response = alertService.createAlert(alert.getTitle(), alert.getMessage(), alert.getType(), alert.getLocation(), alert.getCreatedBy());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllAlerts() {
        Response response = alertService.getAllAlerts();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getAlertTypes() {
        return alertService.getAllAlertTypes();
    }

    @GetMapping("/statuses")
    public List<String> getAlertStatuses() {
        return alertService.getAllAlertStatuses();
    }

    @GetMapping("/alert-by-id/{alertId}")
    public ResponseEntity<Response> getAlertById(@PathVariable("alertId") String alertId) {
        Response response = alertService.getAlertById(alertId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-type")
    public ResponseEntity<Response> getAlertsByType(@RequestParam("type") String type) {
        if (type == null || type.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Alert type is required");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.getAlertsByType(type);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-status")
    public ResponseEntity<Response> getAlertsByStatus(@RequestParam("status") String status) {
        if (status == null || status.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Alert status is required");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.getAlertsByStatus(status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-created-by")
    public ResponseEntity<Response> getAlertsByCreatedBy(@RequestParam("createdBy") String createdBy) {
        if (createdBy == null || createdBy.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Created by is required");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.getAlertsByCreatedBy(createdBy);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-location")
    public ResponseEntity<Response> getAlertsByLocation(@RequestParam("location") String location) {
        if (location == null || location.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Location is required");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.getAlertsByLocation(location);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-type-and-date-range")
    public ResponseEntity<Response> getAlertsByTypeAndDateRange(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        if (type == null || type.isBlank() || startDate == null || endDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("All fields are required (type, startDate, endDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.getAlertsByTypeAndDateRange(type, startDate, endDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-status-and-date-range")
    public ResponseEntity<Response> getAlertsByStatusAndDateRange(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        if (status == null || status.isBlank() || startDate == null || endDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("All fields are required (status, startDate, endDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.getAlertsByStatusAndDateRange(status, startDate, endDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{alertId}")

    public ResponseEntity<Response> updateAlert(
            @PathVariable String alertId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "status", required = false) String status
    ) {

        Response response = alertService.updateAlert(alertId, title, message, type, location, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{alertId}")

    public ResponseEntity<Response> deleteAlert(@PathVariable String alertId) {
        Response response = alertService.deleteAlert(alertId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<Response> getRecentAlerts(@RequestParam(defaultValue = "10") int limit) {
        if (limit <= 0) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Limit must be greater than 0");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.getRecentAlerts(limit);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<Response> getActiveAlerts() {
        Response response = alertService.getActiveAlerts();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/unsent")

    public ResponseEntity<Response> getUnsentAlerts() {
        Response response = alertService.getUnsentAlerts();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/sent")

    public ResponseEntity<Response> getSentAlerts() {
        Response response = alertService.getSentAlerts();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/send/{alertId}")

    public ResponseEntity<Response> sendAlert(@PathVariable String alertId) {
        if (alertId == null || alertId.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Alert ID is required");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.sendAlert(alertId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/archive/{alertId}")

    public ResponseEntity<Response> archiveAlert(@PathVariable String alertId) {
        if (alertId == null || alertId.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Alert ID is required");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = alertService.archiveAlert(alertId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}