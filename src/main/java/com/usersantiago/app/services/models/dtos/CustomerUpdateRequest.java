package com.usersantiago.app.services.models.dtos;

import java.time.LocalDate;

public record CustomerUpdateRequest(String tipoDocument, String firstName, String lastName,
		String phone, LocalDate birthdate) {
}
