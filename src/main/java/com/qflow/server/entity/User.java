package com.qflow.server.entity;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "users")
public class User {

    private int id;
    private String token;
    private String email;
    private Boolean isAdmin;
    private String nameLastname;
    private String password;
    private String profilePicture;
    private String username;


    public User() {
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public String getNameLastname() {
        return nameLastname;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUsername() {
        return username;
    }


    public static final class UserBuilder {
        private int id;
        private String token;
        private String email;
        private Boolean isAdmin;
        private String nameLastname;
        private String password;
        private String profilePicture;
        private String username;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder withToken(String token) {
            this.token = token;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withIsAdmin(Boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public UserBuilder withNameLastname(String nameLastname) {
            this.nameLastname = nameLastname;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public User build() {
            User user = new User();
            user.id = this.id;
            user.username = this.username;
            user.password = this.password;
            user.token = this.token;
            user.nameLastname = this.nameLastname;
            user.profilePicture = this.profilePicture;
            user.email = this.email;
            user.isAdmin = this.isAdmin;
            return user;
        }
    }
}
