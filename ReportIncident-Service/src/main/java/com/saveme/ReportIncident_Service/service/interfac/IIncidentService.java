package com.saveme.ReportIncident_Service.service.interfac;


import com.saveme.ReportIncident_Service.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IIncidentService {

    Response submitIncident(String incidentType, String description, String location,
                            String urgency, Boolean isAnonymous, MultipartFile attachment);

    List<String> getAllIncidentTypes();

    List<String> getAllUrgencyLevels();

    Response getAllIncidents();

    Response deleteIncident(String incidentId);

    Response updateIncident(String incidentId, String incidentType, String description,
                            String location, String urgency, Boolean isAnonymous, MultipartFile attachment);

    Response getIncidentById(String incidentId);

    Response getIncidentsByUrgency(String urgency);

    Response getIncidentsByTypeAndDateRange(String incidentType, LocalDate startDate, LocalDate endDate);

    Response getRecentIncidents(int limit);
}