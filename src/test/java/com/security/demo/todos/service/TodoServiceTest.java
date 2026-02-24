package com.security.demo.todos.service;

import com.security.demo.todos.client.IExternalUserClient;
import com.security.demo.todos.domain.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    private TodoService todoService;
    private IExternalUserClient client;

    @BeforeEach
    public void before() {
        client = mock(IExternalUserClient.class);
        todoService = new TodoService(client);
    }

    @Test
    public void testGetTodos() {
        // given
        List<Todo> todos = List.of(mock(Todo.class), mock(Todo.class), mock(Todo.class));
        when(client.getTodos()).thenReturn(todos);

        // when
        List<Todo> result = todoService.getTodos();

        // then
        assertNotNull(result);
        assertEquals(todos, result);
        verify(client).getTodos();
    }
}
