package com.qflow.server.domain;

import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.domain.repository.UserRepository;
import com.qflow.server.domain.repository.dto.UserDB;
import com.qflow.server.domain.service.UserService;
import com.qflow.server.entity.User;
import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;
import com.qflow.server.entity.exceptions.UserAlreadyExistsException;
import com.qflow.server.entity.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserAdapter userAdapter = new UserAdapter();

    private UserService userService;

    private UserDB userDBMock;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository, userAdapter);
        initializeMocks();
    }

    private void initializeMocks() {
        userDBMock = new UserDB(1, "kilo", "vic@vic.es",
                true, "vic Pepe", "123",
                "luke.jpg","splitter");
    }

    @Test
    void getUserByToken_token_user() {
        Mockito.when(userRepository.findUserByToken("kilo")).thenReturn(Optional.of(userDBMock));

        User res = userService.getUserByToken("kilo");

        assertEquals(res.getUsername(), "splitter");
    }

    @Test
    void getUserByToken_userTokenNotExists_Exception(){
        Mockito.when(userRepository.findUserByToken("kilo")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.getUserByToken("kilo"));
    }

    @Test
    void loginUser_mailPassIsAdmin_token() {
        Mockito.when(userRepository.findUserByEmailAndPassword("vic@vic.es", "123", true))
                .thenReturn(Optional.of(userDBMock));

        String res = userService.loginUser(true, "vic@vic.es", "123");

        assertEquals("kilo", res);
    }

    @Test
    void loginUser_mailPassIsAdmin_LoginNotSuccessfulException(){
        assertThrows(LoginNotSuccesfulException.class, () ->
                this.userService.loginUser(true, "vic@vic", "123"));
    }

    @Test
    void createUser_userExists_UserNotCreatedException(){

        User userToCreate = User.UserBuilder.anUser()
                .withEmail("example")
                .withIsAdmin(true)
                .build();

        UserDB userDB = new UserDB();

        Mockito.when(userRepository.findUserByEmailAndisAdmin("example", true))
                .thenReturn(Optional.of(userDB));

        assertThrows(UserAlreadyExistsException.class, () ->
                this.userService.createUser(userToCreate));
    }

    @Test
    void createUser_userNotExists_na(){


        User userToCreate = User.UserBuilder.anUser()
                .withEmail("example")
                .withIsAdmin(true)
                .build();


        this.userService.createUser(userToCreate);

        Mockito.verify(userRepository).save(Mockito.any());
    }

}
