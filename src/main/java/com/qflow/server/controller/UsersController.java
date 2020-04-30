package com.qflow.server.controller;

import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.entity.User;
import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;
import com.qflow.server.usecase.users.GetUserByToken;
import com.qflow.server.usecase.users.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qflow/user")
public class UsersController {

    private final GetUserByToken getUserByToken;
    private final UserAdapter userAdapter;
    private final LoginUser loginUser;

    public UsersController(@Autowired GetUserByToken getUserByToken,
                           @Autowired UserAdapter userAdapter,
                           @Autowired LoginUser loginUser) {
        this.getUserByToken = getUserByToken;
        this.userAdapter = userAdapter;
        this.loginUser = loginUser;
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<User> getUserByToken(@PathVariable("token") final String token) {
        return new ResponseEntity<>(
                this.getUserByToken.execute(token), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<String> loginUser(
            @RequestHeader(value = "isAdmin") final boolean isAdmin,
            @RequestHeader(value = "mail") String mail,
            @RequestHeader(value = "password") String password) {

        mail = org.owasp.encoder.Encode.forJava(mail);
        password = org.owasp.encoder.Encode.forJava(password);

        try{
            return new ResponseEntity<>(
                    this.loginUser.execute(isAdmin, mail, password), HttpStatus.OK);
        }
        catch (LoginNotSuccesfulException loginNotSuccesfulException){
            return new ResponseEntity<>("Login was not successful", HttpStatus.NO_CONTENT);
        }
    }

}
