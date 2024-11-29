package com.example.apigateway.api_gateway.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider {
    String generateToken(UserDetails userDetails);
}
