package com.example.usuario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class LoginController {

    @GetMapping("test")
    public String test() {
        return "Hello!";
    }
}
