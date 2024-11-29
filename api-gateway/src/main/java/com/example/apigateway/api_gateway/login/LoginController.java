package com.example.apigateway.api_gateway.login;

import com.example.apigateway.api_gateway.security.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final ReactiveUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    @PostMapping("auth/login")
    @ResponseStatus(HttpStatus.OK)
    Mono<LoginResponse> login(@Valid @RequestBody  LoginRequest loginRequest) {
        return userDetailsService.findByUsername(loginRequest.username())
                .filter(u -> passwordEncoder.matches(loginRequest.password(), u.getPassword()))
                .map(tokenProvider::generateToken)
                .map(LoginResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }
}
