package com.saveme.ReportIncident_Service.utills;


import com.saveme.ReportIncident_Service.dto.IncidentDto;
import com.saveme.ReportIncident_Service.model.IncidentReport;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }







    // INCIDENT METHODS
    public static IncidentDto mapIncidentEntityToIncidentDTO(IncidentReport incident) {
        IncidentDto incidentDTO = new IncidentDto();

        incidentDTO.setId(incident.getId());
        incidentDTO.setIncidentType(incident.getIncidentType());
        incidentDTO.setDescription(incident.getDescription());
        incidentDTO.setLocation(incident.getLocation());
        incidentDTO.setUrgency(incident.getUrgency());
        incidentDTO.setIsAnonymous(incident.getIsAnonymous());
        incidentDTO.setAttachmentUrl(incident.getAttachmentUrl());
        incidentDTO.setReportedAt(incident.getReportedAt());

        return incidentDTO;
    }

    public static List<IncidentDto> mapIncidentListEntityToIncidentListDTO(List<IncidentReport> incidentList) {
        return incidentList.stream()
                .map(Utils::mapIncidentEntityToIncidentDTO)
                .collect(Collectors.toList());
    }

    // ALERT METHODS


}