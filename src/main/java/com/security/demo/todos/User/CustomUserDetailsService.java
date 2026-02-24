package com.security.demo.todos.User;

import com.security.demo.todos.Exception.UserNotFoundException;
import com.security.demo.todos.domain.UserEntity;
import com.security.demo.todos.service.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User Not Found"));

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}