package com.base.Alerts_Service.setup;


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








    // ALERT METHODS
    public static AlertDto mapAlertEntityToAlertDTO(Alert alert) {
        AlertDto alertDTO = new AlertDto();

        alertDTO.setId(alert.getId());
        alertDTO.setTitle(alert.getTitle());
        alertDTO.setMessage(alert.getMessage());
        alertDTO.setType(alert.getType());
        alertDTO.setLocation(alert.getLocation());
        alertDTO.setStatus(alert.getStatus());
        alertDTO.setCreatedBy(alert.getCreatedBy());
        alertDTO.setCreatedAt(alert.getCreatedAt());
        alertDTO.setSentAt(alert.getSentAt());

        return alertDTO;
    }

    public static List<AlertDto> mapAlertListEntityToAlertListDTO(List<Alert> alertList) {
        return alertList.stream()
                .map(Utils::mapAlertEntityToAlertDTO)
                .collect(Collectors.toList());
    }
}