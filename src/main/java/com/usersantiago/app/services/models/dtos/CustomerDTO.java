package com.usersantiago.app.services.models.dtos;

import java.util.Date;

public record CustomerDTO(String tipoDocument, String document, String firstName, String lastName, String phone,
		String email, Date birthdate) {
}
