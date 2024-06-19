package com.usersantiago.app.services.models.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreationDTO(
		LocalDate dateReserva,
		String tipoReserva,
		LocalTime hourStart,
		LocalTime hourFinish,
		short quantityPersons,
		String observations,
		String statusReserva,
		Integer idCustomer
) {

}
