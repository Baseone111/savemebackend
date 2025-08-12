package com.base.Alerts_Service.setup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService implements IAlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Override
    public Response createAlert(String title, String message, String type, String location, String createdBy) {
        Response response = new Response();

        try {
            Alert alert = new Alert();
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setType(type);
            alert.setLocation(location);
            alert.setCreatedBy(createdBy);
            alert.setStatus("active");
            alert.setCreatedAt(LocalDateTime.now());

            Alert savedAlert = alertRepository.save(alert);
            AlertDto alertDto = Utils.mapAlertEntityToAlertDTO(savedAlert);

            response.setStatusCode(200);
            response.setMessage("Alert created successfully");
            response.setAlert(alertDto);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while creating alert: " + e.getMessage());
        }

        return response;
    }

    @Override
    public List<String> getAllAlertTypes() {
        return alertRepository.findDistinctAlertTypes();
    }

    @Override
    public List<String> getAllAlertStatuses() {
        return alertRepository.findDistinctAlertStatuses();
    }

    @Override
    public Response getAllAlerts() {
        Response response = new Response();

        try {
            List<Alert> alertList = alertRepository.findAllOrderByCreatedAtDesc();
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alertList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting all alerts: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteAlert(String alertId) {
        Response response = new Response();

        try {
            alertRepository.findById(alertId).orElseThrow(() -> new MyException("Alert Not Found"));
            alertRepository.deleteById(alertId);

            response.setStatusCode(200);
            response.setMessage("Alert deleted successfully");

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting alert: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateAlert(String alertId, String title, String message, String type,
                                String location, String status) {
        Response response = new Response();

        try {
            Alert alert = alertRepository.findById(alertId)
                    .orElseThrow(() -> new MyException("Alert Not Found"));

            if (title != null && !title.isBlank()) alert.setTitle(title);
            if (message != null && !message.isBlank()) alert.setMessage(message);
            if (type != null && !type.isBlank()) alert.setType(type);
            if (location != null && !location.isBlank()) alert.setLocation(location);
            if (status != null && !status.isBlank()) alert.setStatus(status);

            Alert updatedAlert = alertRepository.save(alert);
            AlertDto alertDto = Utils.mapAlertEntityToAlertDTO(updatedAlert);

            response.setStatusCode(200);
            response.setMessage("Alert updated successfully");
            response.setAlert(alertDto);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating alert: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAlertById(String alertId) {
        Response response = new Response();

        try {
            Alert alert = alertRepository.findById(alertId)
                    .orElseThrow(() -> new MyException("Alert Not Found"));
            AlertDto alertDto = Utils.mapAlertEntityToAlertDTO(alert);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlert(alertDto);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting alert by id: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAlertsByType(String type) {
        Response response = new Response();

        try {
            List<Alert> alerts = alertRepository.findByTypeOrderByCreatedAtDesc(type);
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting alerts by type: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAlertsByStatus(String status) {
        Response response = new Response();

        try {
            List<Alert> alerts = alertRepository.findByStatusOrderByCreatedAtDesc(status);
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting alerts by status: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAlertsByCreatedBy(String createdBy) {
        Response response = new Response();

        try {
            List<Alert> alerts = alertRepository.findByCreatedByOrderByCreatedAtDesc(createdBy);
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting alerts by created by: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAlertsByLocation(String location) {
        Response response = new Response();

        try {
            List<Alert> alerts = alertRepository.findByLocationOrderByCreatedAtDesc(location);
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting alerts by location: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAlertsByTypeAndDateRange(String type, LocalDate startDate, LocalDate endDate) {
        Response response = new Response();

        try {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            List<Alert> alerts = alertRepository.findByTypeAndCreatedAtBetweenOrderByCreatedAtDesc(
                    type, startDateTime, endDateTime);
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting alerts by type and date range: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAlertsByStatusAndDateRange(String status, LocalDate startDate, LocalDate endDate) {
        Response response = new Response();

        try {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            List<Alert> alerts = alertRepository.findByStatusAndCreatedAtBetweenOrderByCreatedAtDesc(
                    status, startDateTime, endDateTime);
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting alerts by status and date range: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getRecentAlerts(int limit) {
        Response response = new Response();

        try {
            Pageable pageable = PageRequest.of(0, limit);
            List<Alert> alerts = alertRepository.findRecentAlerts(pageable);
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting recent alerts: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getActiveAlerts() {
        Response response = new Response();

        try {
            List<Alert> alerts = alertRepository.findActiveAlerts();
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting active alerts: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUnsentAlerts() {
        Response response = new Response();

        try {
            List<Alert> alerts = alertRepository.findUnsentAlerts();
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting unsent alerts: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getSentAlerts() {
        Response response = new Response();

        try {
            List<Alert> alerts = alertRepository.findSentAlerts();
            List<AlertDto> alertDtoList = Utils.mapAlertListEntityToAlertListDTO(alerts);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setAlertList(alertDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting sent alerts: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response sendAlert(String alertId) {
        Response response = new Response();

        try {
            Alert alert = alertRepository.findById(alertId)
                    .orElseThrow(() -> new MyException("Alert Not Found"));

            alert.setSentAt(LocalDateTime.now());
            Alert updatedAlert = alertRepository.save(alert);
            AlertDto alertDto = Utils.mapAlertEntityToAlertDTO(updatedAlert);

            response.setStatusCode(200);
            response.setMessage("Alert sent successfully");
            response.setAlert(alertDto);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while sending alert: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response archiveAlert(String alertId) {
        Response response = new Response();

        try {
            Alert alert = alertRepository.findById(alertId)
                    .orElseThrow(() -> new MyException("Alert Not Found"));

            alert.setStatus("archived");
            Alert updatedAlert = alertRepository.save(alert);
            AlertDto alertDto = Utils.mapAlertEntityToAlertDTO(updatedAlert);

            response.setStatusCode(200);
            response.setMessage("Alert archived successfully");
            response.setAlert(alertDto);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while archiving alert: " + e.getMessage());
        }

        return response;
    }
}