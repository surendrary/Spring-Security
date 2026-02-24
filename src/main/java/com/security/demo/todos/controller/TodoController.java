package com.security.demo.todos.controller;

import com.security.demo.todos.domain.Todo;
import com.security.demo.todos.service.ITodoService;
import com.security.demo.todos.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TodoController {


    private ITodoService todoService;
    private IUserService iUserService;

    TodoController(ITodoService todoService,IUserService iUserService){
        this.todoService = todoService;
        this.iUserService = iUserService;
    }

    @GetMapping(value = "/ven-todos", produces = APPLICATION_JSON_VALUE)
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping(value ="/ven-todos/filter")
    public List<Todo> getTodosByUsername(@RequestParam("username") String username){
        return todoService.getTodosByUserName(username);


    }
}
