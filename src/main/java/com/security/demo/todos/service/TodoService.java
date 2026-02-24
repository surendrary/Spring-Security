package com.security.demo.todos.service;

import com.security.demo.todos.client.IExternalUserClient;
import com.security.demo.todos.domain.Todo;
import com.security.demo.todos.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService implements ITodoService {

    private final IExternalUserClient externalUserClient;

    public TodoService(IExternalUserClient externalUserClient) {
        this.externalUserClient = externalUserClient;
    }

    @Override
    public List<Todo> getTodos() {
        return externalUserClient.getTodos();
    }

    @Override
    public List<Todo> getTodosByUserName(String userName) {

        User user = externalUserClient.getUsers().stream().filter(x -> x.getUsername().equalsIgnoreCase(userName)).findFirst().orElse(null);

        if(user == null) return List.of();

        return externalUserClient.getTodos().stream().filter(x -> x.getUserId() == user.getId()).toList();



    }
}
