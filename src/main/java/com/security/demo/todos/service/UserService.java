package com.security.demo.todos.service;

import com.security.demo.todos.Exception.UserNotFoundException;
import com.security.demo.todos.client.IExternalUserClient;
import com.security.demo.todos.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IExternalUserClient externalUserClient;

    public UserService(IExternalUserClient externalUserClient) {
        this.externalUserClient = externalUserClient;
    }

    @Override
    public List<User> getUsers() {
        return externalUserClient.getUsers();
    }

    @Override
    public User getUser(long id) {

        return externalUserClient.getUsers().stream().filter(x -> x.getId() == id).findFirst().orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND,"User Not Found"));


    }
}
