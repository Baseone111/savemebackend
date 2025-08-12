package com.base.Missing_Person_Service.utills;


import com.base.Missing_Person_Service.dto.MissingPersonDto;
import com.base.Missing_Person_Service.model.MissingPerson;

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




    // MISSING PERSON METHODS
    public static MissingPersonDto mapMissingPersonEntityToMissingPersonDto(MissingPerson missingPerson){
        MissingPersonDto missingPersonDto = new MissingPersonDto();
        missingPersonDto.setId(missingPerson.getId());
        missingPersonDto.setAge(missingPerson.getAge());
        missingPersonDto.setDescription(missingPerson.getDescription());
        missingPersonDto.setFullName(missingPerson.getFullName());
        missingPersonDto.setAttachmentUrl(missingPerson.getAttachmentUrl());
        missingPersonDto.setLastSeenDate(missingPerson.getLastSeenDate().toLocalDate());
        missingPersonDto.setRelationship(missingPerson.getRelationship());
        missingPersonDto.setLastSeenLocation(missingPerson.getLastSeenLocation());
        return missingPersonDto;
    }

    public static List<MissingPersonDto> mapMissingPersonListEntityToMissingPersonListDto(List<MissingPerson> missingPersonList){
        return missingPersonList.stream()
                .map(Utils::mapMissingPersonEntityToMissingPersonDto)
                .collect(Collectors.toList());
    }




}