package com.usersantiago.app.controllers;

import org.springframework.http.ResponseEntity;
import com.usersantiago.app.response.MessageResponse;
import com.usersantiago.app.services.ReservaService;
import com.usersantiago.app.services.models.dtos.ReservationCreationDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@ResponseBody
@ResponseStatus
@RequestMapping("/api/v1/reservas")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

	private ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createReserva(@RequestBody ReservationCreationDTO reservaRequest) {
		System.out.println("reserva" + reservaRequest);
		reservaService.createReserva(reservaRequest);
		return ResponseEntity.status(201).body((new MessageResponse("Reserva registrada existosamente!")));
	}
}
