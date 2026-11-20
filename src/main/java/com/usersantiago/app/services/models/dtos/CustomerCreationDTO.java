package com.usersantiago.app.services.models.dtos;

import java.time.LocalDate;

public record CustomerCreationDTO (
    String documentType,
    String documentNumber,
    String firstName,
    String lastName,
    String phone,
    String email,
    String password_cust,
    LocalDate birthdate) {
}
