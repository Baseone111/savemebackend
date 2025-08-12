package com.saveme.ReportIncident_Service.service;

import com.saveme.ReportIncident_Service.dto.IncidentDto;
import com.saveme.ReportIncident_Service.dto.Response;
import com.saveme.ReportIncident_Service.exception.MyException;
import com.saveme.ReportIncident_Service.model.IncidentReport;
import com.saveme.ReportIncident_Service.repository.IncidentRepository;
import com.saveme.ReportIncident_Service.service.interfac.IIncidentService;
import com.saveme.ReportIncident_Service.utills.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService implements IIncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

//    @Autowired
//    private AwsS3Service awsS3Service;


    public Response submitIncident(String incidentType, String description, String location,
                                   String urgency, Boolean isAnonymous, MultipartFile attachment) {

        Response response = new Response();



            IncidentReport incident = new IncidentReport();
            incident.setIncidentType(incidentType);
            incident.setDescription(description);
            incident.setLocation(location);
            incident.setUrgency(urgency);
            incident.setIsAnonymous(isAnonymous != null ? isAnonymous : false);
            incident.setAttachmentUrl(incident.getAttachmentUrl());
            incident.setReportedAt(LocalDateTime.now());

            IncidentReport savedIncident = incidentRepository.save(incident);
            IncidentDto incidentDto = Utils.mapIncidentEntityToIncidentDTO(savedIncident);

            response.setStatusCode(200);
            response.setMessage("Incident reported successfully");
            response.setIncident(incidentDto);


        return response;
    }


    public List<String> getAllIncidentTypes() {
        return incidentRepository.findDistinctIncidentTypes();
    }


    public List<String> getAllUrgencyLevels() {
        return incidentRepository.findDistinctUrgencyLevels();
    }


    public Response getAllIncidents() {
        Response response = new Response();

        try {
            List<IncidentReport> incidentList = incidentRepository.findAllOrderByReportedAtDesc();
            List<IncidentDto> incidentDtoList = Utils.mapIncidentListEntityToIncidentListDTO(incidentList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setIncidentList(incidentDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting all incidents: " + e.getMessage());
        }

        return response;
    }


    public Response deleteIncident(String incidentId) {
        Response response = new Response();

        try {
            incidentRepository.findById(incidentId).orElseThrow(() -> new MyException("Incident Not Found"));
            incidentRepository.deleteById(incidentId);

            response.setStatusCode(200);
            response.setMessage("Incident deleted successfully");

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting incident: " + e.getMessage());
        }

        return response;
    }


    public Response updateIncident(String incidentId, String incidentType, String description,
                                   String location, String urgency, Boolean isAnonymous, MultipartFile attachment) {
        Response response = new Response();



            IncidentReport incident = incidentRepository.findById(incidentId)
                    .orElseThrow(() -> new MyException("Incident Not Found"));

            if (incidentType != null && !incidentType.isBlank()) incident.setIncidentType(incidentType);
            if (description != null && !description.isBlank()) incident.setDescription(description);
            if (location != null && !location.isBlank()) incident.setLocation(location);
            if (urgency != null && !urgency.isBlank()) incident.setUrgency(urgency);
            if (isAnonymous != null) incident.setIsAnonymous(isAnonymous);
            if (attachment != null) incident.setAttachmentUrl(String.valueOf(attachment));

            IncidentReport updatedIncident = incidentRepository.save(incident);
            IncidentDto incidentDto = Utils.mapIncidentEntityToIncidentDTO(updatedIncident);

            response.setStatusCode(200);
            response.setMessage("Incident updated successfully");
            response.setIncident(incidentDto);



        return response;
    }


    public Response getIncidentById(String incidentId) {
        Response response = new Response();

        try {
            IncidentReport incident = incidentRepository.findById(incidentId)
                    .orElseThrow(() -> new MyException("Incident Not Found"));
            IncidentDto incidentDto = Utils.mapIncidentEntityToIncidentDTO(incident);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setIncident(incidentDto);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting incident by id: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getIncidentsByUrgency(String urgency) {
        Response response = new Response();

        try {
            List<IncidentReport> incidents = incidentRepository.findByUrgencyOrderByReportedAtDesc(urgency);
            List<IncidentDto> incidentDtoList = Utils.mapIncidentListEntityToIncidentListDTO(incidents);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setIncidentList(incidentDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting incidents by urgency: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getIncidentsByTypeAndDateRange(String incidentType, LocalDate startDate, LocalDate endDate) {
        Response response = new Response();

        try {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            List<IncidentReport> incidents = incidentRepository.findByIncidentTypeAndReportedAtBetweenOrderByReportedAtDesc(
                    incidentType, startDateTime, endDateTime);
            List<IncidentDto> incidentDtoList = Utils.mapIncidentListEntityToIncidentListDTO(incidents);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setIncidentList(incidentDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting incidents by type and date range: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getRecentIncidents(int limit) {
        Response response = new Response();

        try {
            Pageable pageable = PageRequest.of(0, limit);
            List<IncidentReport> incidents = incidentRepository.findRecentIncidents(pageable);
            List<IncidentDto> incidentDtoList = Utils.mapIncidentListEntityToIncidentListDTO(incidents);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setIncidentList(incidentDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting recent incidents: " + e.getMessage());
        }

        return response ;
    }
}
