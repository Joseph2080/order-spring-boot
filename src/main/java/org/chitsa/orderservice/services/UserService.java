package org.chitsa.orderservice.services;


import org.chitsa.orderservice.dto.LoginRequestDto;
import org.chitsa.orderservice.dto.UserRegisterDto;

import java.util.Map;

public interface UserService {
    String createUser(UserRegisterDto userDto);
    void deleteUser(String username);
    boolean doesUserExistsById(String userId);
    void deleteAllUsers();
    Map<String, String> loginUser(LoginRequestDto loginRequestDto);
}
