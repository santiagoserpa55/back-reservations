package com.usersantiago.app.services.models.dtos;

import java.time.LocalDate;

public record CustomerCreationDTO (
    String tipoDocument,
    String document,
    String firstName,
    String lastName,
    String phone,
    String email,
    String password,
    LocalDate birthdate) {
}
