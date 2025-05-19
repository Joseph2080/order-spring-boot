package org.chitsa.orderservice.services;


import org.chitsa.orderservice.dto.LoginRequestDto;

import java.util.Map;

public interface UserService {
    String createUser(LoginRequestDto.UserRegisterDto userDto);
    void deleteUser(String username);
    boolean doesUserExistsById(String userId);
    void deleteAllUsers();
    Map<String, String> loginUser(LoginRequestDto loginRequestDto);
}
