package com.usersantiago.app.services.models.dtos;

import java.time.LocalDateTime;

public record ReservationCreationDTO(
		LocalDateTime dateReserva,
		String tipoReserva,
		short quantityPersons,
		String observations,
		String statusReserva,
		Integer idCustomer
) {

}
