package com.base.User_Service.dto;

import com.base.User_Service.dto.UserDto;
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

    private UserDto user;
    private List<UserDto> userList;

    private Object data;

    // Incident fields


    // Alert fields




}