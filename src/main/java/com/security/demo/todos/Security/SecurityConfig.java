package com.security.demo.todos.Security;

import com.security.demo.todos.JWTAuthorization.JWTAuthFilter;
import com.security.demo.todos.User.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;


    // Basic Http Filtering
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth ->
                auth.requestMatchers(HttpMethod.GET,"/ven-todos/**").permitAll()
                .requestMatchers("/ven-users/**").denyAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    // 🔒 Triggered when user is NOT authenticated (401)
                    logger.warn("UNAUTHORIZED REQUEST - URI: {} | Reason: {}",
                            request.getRequestURI(), authException.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 🚫 Triggered when user IS authenticated but lacks permission (403)
                    logger.warn("ACCESS DENIED - URI: {} | Reason: {}",
                            request.getRequestURI(), accessDeniedException.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                })
        );

        return http.build();
    } */

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                auth.requestMatchers("/error").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/ven-users/**").hasRole("ADMIN")
                        .requestMatchers("/ven-todos/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
        )       .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 🔒 Triggered when user is NOT authenticated (401)
                            log.warn("UNAUTHORIZED REQUEST - URI: {} | Reason: {}",
                                    request.getRequestURI(), authException.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // 🚫 Triggered when user IS authenticated but lacks permission (403)
                            log.warn("ACCESS DENIED - URI: {} | Reason: {}",
                                    request.getRequestURI(), accessDeniedException.getMessage());
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                        })
                );
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
