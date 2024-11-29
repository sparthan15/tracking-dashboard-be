package com.example.apigateway.api_gateway.login;

import jakarta.validation.constraints.NotEmpty;

record LoginRequest(@NotEmpty String username, @NotEmpty String password) {
}
