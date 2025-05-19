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

    public static class UserRegisterDto {
        private String email;
        private String phoneNumber;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "UserRequestDto{" +
                    "email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}