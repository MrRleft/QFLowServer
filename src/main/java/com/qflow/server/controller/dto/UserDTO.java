package com.qflow.server.controller.dto;

public class UserDTO {

    String username;
    String email;
    String token;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }


    public static final class UserDTOBuilder {
        String username;
        String email;
        String token;

        private UserDTOBuilder() {
        }

        public static UserDTOBuilder anUserDTO() {
            return new UserDTOBuilder();
        }

        public UserDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder token(String token) {
            this.token = token;
            return this;
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO();
            userDTO.email = this.email;
            userDTO.username = this.username;
            userDTO.token = this.token;
            return userDTO;
        }
    }
}
