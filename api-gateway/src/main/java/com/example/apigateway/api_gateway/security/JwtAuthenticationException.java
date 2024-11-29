package com.example.apigateway.api_gateway.security;

import org.springframework.security.core.AuthenticationException;

class JwtAuthenticationException extends AuthenticationException {
    JwtAuthenticationException(String msg) {
        super(msg);
    }
}
