package com.base.Alerts_Service.setup;

import java.time.LocalDate;
import java.util.List;

public interface IAlertService {

    Response createAlert(String title, String message, String type, String location, String createdBy);

    List<String> getAllAlertTypes();

    List<String> getAllAlertStatuses();

    Response getAllAlerts();

    Response deleteAlert(String alertId);

    Response updateAlert(String alertId, String title, String message, String type,
                         String location, String status);

    Response getAlertById(String alertId);

    Response getAlertsByType(String type);

    Response getAlertsByStatus(String status);

    Response getAlertsByCreatedBy(String createdBy);

    Response getAlertsByLocation(String location);

    Response getAlertsByTypeAndDateRange(String type, LocalDate startDate, LocalDate endDate);

    Response getAlertsByStatusAndDateRange(String status, LocalDate startDate, LocalDate endDate);

    Response getRecentAlerts(int limit);

    Response getActiveAlerts();

    Response getUnsentAlerts();

    Response getSentAlerts();

    Response sendAlert(String alertId);

    Response archiveAlert(String alertId);
}