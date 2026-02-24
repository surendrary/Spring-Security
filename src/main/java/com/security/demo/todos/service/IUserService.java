package com.security.demo.todos.service;

import com.security.demo.todos.domain.User;

import java.util.List;

public interface IUserService {

    List<User> getUsers();

    User getUser(long id);
}
