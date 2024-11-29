package com.example.usuario.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @UuidGenerator
    private UUID id;
    private String nombres;
    private String apellidos;
    private String nombreUsuario;
    private String password;
}
