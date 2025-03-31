package org.chitsa.orderservice.dto;

public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequestDto(){

    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}