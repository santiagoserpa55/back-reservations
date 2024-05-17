package com.usersantiago.app.services;

import org.springframework.stereotype.Service;

import com.usersantiago.app.persistence.entities.ReservationEntity;
import com.usersantiago.app.persistence.repositories.ReservasRepository;
import com.usersantiago.app.services.models.dtos.ReservationCreationDTO;

@Service
public class ReservaService {

	private ReservasRepository reservaRepository;
	
	public ReservaService(ReservasRepository reservaRepository) {
		this.reservaRepository = reservaRepository;
	}

	public void createReserva(ReservationCreationDTO reservaRequest) {
		ReservationEntity newReservation = new ReservationEntity(
				reservaRequest.dateReserva(),
				reservaRequest.tipoReserva(), 
				reservaRequest.quantityPersons(),
				reservaRequest.observations(),
				reservaRequest.statusReserva(),
				reservaRequest.idCustomer()
				);
		reservaRepository.saveReserva(newReservation);	
	}

}
