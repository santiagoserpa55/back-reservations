package com.usersantiago.app.services.models.dtos;

import java.time.LocalDate;

public record CustomerDTO(
				Integer customerId,
				String tipoDocument,
				String document,
				String firstName,
				String lastName,
				String phone,
				String email,
				LocalDate birthdate) {
}
