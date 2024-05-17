package com.usersantiago.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.usersantiago.app.response.MessageResponse;
import com.usersantiago.app.services.ReservaService;
import com.usersantiago.app.services.models.dtos.ReservationCreationDTO;

@RestController
@ResponseBody
@ResponseStatus
@RequestMapping("/api/v1/reservas")

public class ReservaController {

	private ReservaService reservaService;
	private static final String REQUEST_CREATE_RESERVA = "/create";

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@PostMapping(REQUEST_CREATE_RESERVA)
	public ResponseEntity<?> createReserva(@RequestBody ReservationCreationDTO reservaRequest) {
		
		reservaService.createReserva(reservaRequest);
		return ResponseEntity.status(201).body((new MessageResponse("Reserva registrada existosamente!")));

	}

}
