package com.security.demo.todos.Exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(HttpStatusCode status, @Nullable String reason) {
        super(status, reason);
    }
}
