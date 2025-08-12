package com.base.User_Service.service.interfac;


import com.base.User_Service.dto.LoginRequest;
import com.base.User_Service.dto.Response;
import com.base.User_Service.model.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUSerBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
