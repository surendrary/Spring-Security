package com.security.demo.todos.controller;

import com.security.demo.todos.DTO.LoginRequest;
import com.security.demo.todos.DTO.LoginResponse;
import com.security.demo.todos.DTO.RegisterRequest;
import com.security.demo.todos.JWTAuthorization.JWTUtil;
import com.security.demo.todos.domain.UserEntity;
import com.security.demo.todos.service.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserDetailsRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterRequest request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("{\"message\": \"Username already exists\"}");
        }
        UserEntity user = new UserEntity();
        user.setRole(request.getRole());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        userRepository.save(user);
        log.info("REGISTERED - User : {} , Role : {}",user.getUsername(),user.getRole());
        return ResponseEntity.ok("{\"message\": \"User registered successfully\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request){
        try{
            UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtUtil.generateToken(request.getUsername(),"ROLE_" + user.getRole());
            log.info("LOGIN SUCCESS - User: {}", request.getUsername());
            return ResponseEntity.ok(new LoginResponse(token));


        }catch (BadCredentialsException e){
            log.warn("LOGIN FAILED - User: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Invalid username or password\"}");
        }

    }


}
