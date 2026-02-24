package com.security.demo.todos.service;

import com.security.demo.todos.domain.Todo;

import java.util.List;

public interface ITodoService {

    List<Todo> getTodos();

    List<Todo> getTodosByUserName(String userName);
}
