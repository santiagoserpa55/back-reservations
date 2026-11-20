package com.usersantiago.app.services.models.dtos;

import java.time.LocalDate;

public record CustomerUpdateRequest(
		String document_type,
		String firstName,
		String lastName,
		String phone,
		LocalDate birthdate) {
}
