package com.security.demo.todos.client;

import com.security.demo.todos.domain.Todo;
import com.security.demo.todos.domain.User;

import java.util.List;

public interface IExternalUserClient {

    List<User> getUsers();

    List<Todo> getTodos();
}
