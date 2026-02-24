package com.security.demo.todos.client;

import com.security.demo.todos.domain.Todo;
import com.security.demo.todos.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JsonPlaceholderClient implements IExternalUserClient {

    private static final String URL = "https://jsonplaceholder.typicode.com/";
    private static final String URL_USERS = URL + "users";
    private static final String URL_TODO = URL + "todos";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<User> getUsers() {
        return getForList(URL_USERS, User[].class);
    }

    @Override
    public List<Todo> getTodos() {
        return getForList(URL_TODO, Todo[].class);
    }

    private <T> List<T> getForList(String url, Class<T[]> clazz) {
        T[] array = restTemplate.getForObject(url, clazz);
        return array == null ? List.of() : Arrays.stream(array).collect(Collectors.toSet()).stream().toList();
    }
}
