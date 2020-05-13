package com.qflow.server.domain.service;

import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.domain.repository.UserRepository;
import com.qflow.server.domain.repository.dto.UserDB;
import com.qflow.server.entity.User;
import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;
import com.qflow.server.entity.exceptions.UserAlreadyExistsException;
import com.qflow.server.entity.exceptions.UserNotFoundException;
import com.qflow.server.usecase.users.CreateUserDatabase;
import com.qflow.server.usecase.users.GetUserByTokenDatabase;
import com.qflow.server.usecase.users.LoginUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserService implements GetUserByTokenDatabase, LoginUserDatabase, CreateUserDatabase {

    final private UserRepository userRepository;
    final private UserAdapter userAdapter;

    public UserService(@Autowired final UserRepository userRepository,
                       @Autowired final UserAdapter userAdapter) {
        this.userRepository = userRepository;
        this.userAdapter = userAdapter;
    }

    @Override
    public User getUserByToken(String token) {
        Optional<UserDB> userDBOptional = userRepository.findUserByToken(token);
        if(!userDBOptional.isPresent()){
            throw new UserNotFoundException("User with token: " + token + " not found");
        }
        return userAdapter.userDBToUser(userDBOptional.get());
    }

    @Override
    public User loginUser(boolean isAdmin, String mail, String password) throws LoginNotSuccesfulException{

        Optional<UserDB> user = userRepository.findUserByEmailAndPassword(mail, password, isAdmin);
        if(!user.isPresent()){
            throw new LoginNotSuccesfulException("Login not successful");
        }
        return userAdapter.userDBToUser(user.get());
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException{

        if(!userRepository.findUserByEmailAndisAdmin(user.getEmail(), user.getIsAdmin()).isPresent()) {
            String token = generateToken();
            UserDB userToCreate = new UserDB(token, user.getEmail(), user.getIsAdmin(), user.getNameLastname(),
                    user.getPassword(), "", user.getUsername());
            userToCreate = userRepository.save(userToCreate);
            return userAdapter.userDBToUser(userToCreate);
        }
        else{
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " with admin situation as " +
                    user.getIsAdmin() + " already exists");
        }
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[50];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;
    }
}
