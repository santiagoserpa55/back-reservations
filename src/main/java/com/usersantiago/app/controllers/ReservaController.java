package com.usersantiago.app.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.usersantiago.app.api.Mapper;
import com.usersantiago.app.response.MessageResponse;
import com.usersantiago.app.services.ReservaService;
import com.usersantiago.app.services.models.dtos.GetReservasDTO;
import com.usersantiago.app.services.models.dtos.ReservationCreationDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private final ReservaService reservaService;
	private Mapper mapper;

	public ReservaController(ReservaService reservaService, Mapper mapper) {
		this.reservaService = reservaService;
		this.mapper = mapper;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createReserva(@RequestBody ReservationCreationDTO reservaRequest) {
		reservaService.createReserva(reservaRequest);
		return ResponseEntity.status(201).body((new MessageResponse("Reserva registrada existosamente!")));
	}
	
    @GetMapping(value = "{id}")
    public ResponseEntity<Map<String, List<GetReservasDTO>>> getReservas(@PathVariable Integer id) {
        List<GetReservasDTO> reservas = reservaService.getBookings(id)
                .stream()
                .map(mapper::toDTOBooking)
                .toList();
        Map<String, List<GetReservasDTO>> responseBody = Map.of("data", reservas);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
	
}
