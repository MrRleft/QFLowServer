package com.qflow.server.domain;

import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.domain.repository.UserRepository;
import com.qflow.server.domain.repository.dto.UserDB;
import com.qflow.server.domain.service.UserService;
import com.qflow.server.entity.User;
import com.qflow.server.entity.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Mockito.when(userRepository.findUserByToken("kilo"))
                .thenReturn(Optional.of(userDBMock));

        User res = userService.getUserByToken("kilo");

        assertEquals(res.getUsername(), "splitter");
    }

    @Test
    void getUserByToken_userTokenNotExists_Exception(){
        Mockito.when(userRepository.findUserByToken("kilo")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.getUserByToken("kilo"));
    }

}
