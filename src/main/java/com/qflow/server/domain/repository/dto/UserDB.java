package com.qflow.server.domain.repository.dto;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 256)
    private String token;

    @Column(length = 256)
    private String email;

    @Column(name = "is_admin", length = 256)
    private Boolean isAdmin;

    @Column(name = "name_lastname", length = 256)
    private String nameLastname;

    @Column(length = 256)
    private String password;

    @Column(name = "profile_picture", length = 256)
    private String profilePicture;

    @Column(length = 256)
    private String username;


    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getAdmin() {
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

    public UserDB() {
    }

    public UserDB(Integer id, String token, String email, Boolean isAdmin,
                  String nameLastname, String password,
                  String profilePicture, String username) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.isAdmin = isAdmin;
        this.nameLastname = nameLastname;
        this.password = password;
        this.profilePicture = profilePicture;
        this.username = username;
    }
}
