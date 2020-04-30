package com.qflow.server.controller.dto;

public class UserPost {

    private String username;
    private String password;
    private String email;
    private String nameLastName;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNameLastName() {
        return nameLastName;
    }


    public static final class UserPostBuilder {
        private String userName;
        private String password;
        private String email;
        private String nameLastName;

        private UserPostBuilder() {
        }

        public static UserPostBuilder anUserPost() {
            return new UserPostBuilder();
        }

        public UserPostBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserPostBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserPostBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserPostBuilder withNameLastName(String nameLastName) {
            this.nameLastName = nameLastName;
            return this;
        }

        public UserPost build() {
            UserPost userPost = new UserPost();
            userPost.email = this.email;
            userPost.nameLastName = this.nameLastName;
            userPost.username = this.userName;
            userPost.password = this.password;
            return userPost;
        }
    }
}
