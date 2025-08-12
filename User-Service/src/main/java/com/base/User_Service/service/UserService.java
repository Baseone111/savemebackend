package com.base.User_Service.service;



import com.base.User_Service.MyException;
import com.base.User_Service.dto.LoginRequest;
import com.base.User_Service.dto.Response;
import com.base.User_Service.dto.UserDto;
import com.base.User_Service.model.User;
import com.base.User_Service.repository.UserRepository;
import com.base.User_Service.service.interfac.IUserService;
import com.base.User_Service.utils.JWTUtils;
import com.base.User_Service.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Response register(User user) {

        Response response = new Response();

        try {

            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }

            if (userRepository.existsByEmail(user.getId())) {
                throw new MyException("Email Already Exists");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);

            UserDto userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage( "Error while registering a user: " +e.getMessage());

        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new MyException("USer Not Found"));
            var token = jwtUtils.generateJWTToken(user);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage( "Error while logging in a user: " +e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllUsers() {

        Response response = new Response();

        try {
            List<User> userList = userRepository.findAll();
            List<UserDto> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDTOList);

        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage( "Error while getting all users: " +e.getMessage());

        }
        return response;
    }

    @Override
    public Response getUSerBookingHistory(String userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(userId).orElseThrow(()-> new MyException("User Not Found"));
            UserDto userDTO= Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        }catch (MyException e){
            response.setStatusCode(404);
            response.setMessage( e.getMessage());

        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage( "Error while getting user booking history: " +e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {

        Response response = new Response();

        try {
            userRepository.findById(userId).orElseThrow(()-> new MyException("User Not Found"));
            userRepository.deleteById(userId);
            response.setStatusCode(200);
            response.setMessage("successful");

        }catch (MyException e){
            response.setStatusCode(404);
            response.setMessage( e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage( "Error while deleting a user: " +e.getMessage());

        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(userId).orElseThrow(()-> new MyException("User Not Found"));
            UserDto userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        }catch (MyException e){
            response.setStatusCode(404);
            response.setMessage( e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage( "Error while getting a user by id : " +e.getMessage());

        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {

        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new MyException("User Not Found"));
            UserDto userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        }catch (MyException e){
            response.setStatusCode(404);
            response.setMessage( e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage( "Error while getting a user infor: " +e.getMessage());

        }
        return response;
    }
}
