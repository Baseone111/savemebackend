package com.base.Missing_Person_Service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private String message;
    private String role;
    private String token;
    private String expirationTime;



    private Object data;

    // Incident fields


    // Missing person fields
    private MissingPersonDto missingPerson;
    private List<MissingPersonDto> missingPersonList;




    public MissingPersonDto getMissingPerson() {
        return missingPerson;
    }

    public void setMissingPerson(MissingPersonDto missingPerson) {
        this.missingPerson = missingPerson;
    }

    public List<MissingPersonDto> getMissingPersonDtoList() {
        return missingPersonList;
    }

    public void setMissingPersonDtoList(List<MissingPersonDto> missingPersonDtoList) {
        this.missingPersonList = missingPersonDtoList;
    }


}