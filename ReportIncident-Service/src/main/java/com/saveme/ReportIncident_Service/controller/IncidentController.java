package com.saveme.ReportIncident_Service.controller;


import com.saveme.ReportIncident_Service.dto.Response;
import com.saveme.ReportIncident_Service.service.interfac.IIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "*")
public class IncidentController {

    @Autowired
    private IIncidentService incidentService;

    @PostMapping("/submit")

    public ResponseEntity<Response> submitIncident(
            @RequestParam(value = "incidentType", required = false) String incidentType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "urgency", required = false) String urgency,
            @RequestParam(value = "isAnonymous", required = false, defaultValue = "false") Boolean isAnonymous,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment
    ) {

        if (incidentType == null || incidentType.isBlank() ||
                description == null || description.isBlank() ||
                location == null || location.isBlank() ||
                urgency == null || urgency.isBlank()) {

            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all required fields (incidentType, description, location, urgency)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = incidentService.submitIncident(incidentType, description, location, urgency, isAnonymous, attachment);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllIncidents() {
        Response response = incidentService.getAllIncidents();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getIncidentTypes() {
        return incidentService.getAllIncidentTypes();
    }

    @GetMapping("/urgency-levels")
    public List<String> getUrgencyLevels() {
        return incidentService.getAllUrgencyLevels();
    }

    @GetMapping("/incident-by-id/{incidentId}")
    public ResponseEntity<Response> getIncidentById(@PathVariable("incidentId") String incidentId) {
        Response response = incidentService.getIncidentById(incidentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-urgency")
    public ResponseEntity<Response> getIncidentsByUrgency(@RequestParam("urgency") String urgency) {
        if (urgency == null || urgency.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Urgency level is required");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = incidentService.getIncidentsByUrgency(urgency);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/by-type-and-date-range")
    public ResponseEntity<Response> getIncidentsByTypeAndDateRange(
            @RequestParam(required = false) String incidentType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        if (incidentType == null || incidentType.isBlank() || startDate == null || endDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("All fields are required (incidentType, startDate, endDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = incidentService.getIncidentsByTypeAndDateRange(incidentType, startDate, endDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{incidentId}")

    public ResponseEntity<Response> updateIncident(
            @PathVariable String incidentId,
            @RequestParam(value = "incidentType", required = false) String incidentType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "urgency", required = false) String urgency,
            @RequestParam(value = "isAnonymous", required = false) Boolean isAnonymous,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment
    ) {

        Response response = incidentService.updateIncident(incidentId, incidentType, description, location, urgency, isAnonymous, attachment);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{incidentId}")

    public ResponseEntity<Response> deleteIncident(@PathVariable String incidentId) {
        Response response = incidentService.deleteIncident(incidentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<Response> getRecentIncidents(@RequestParam(defaultValue = "10") int limit) {
        Response response = incidentService.getRecentIncidents(limit);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}