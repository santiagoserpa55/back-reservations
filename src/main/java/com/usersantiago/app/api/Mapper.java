package com.usersantiago.app.api;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.persistence.entities.ReservationEntity;
import com.usersantiago.app.services.models.dtos.CustomerDTO;
import com.usersantiago.app.services.models.dtos.GetReservasDTO;

@Component
public class Mapper {
	
	public CustomerDTO toDTO(CustomerEntity customerEntity) {
		int customerId = customerEntity.getIdCustomer();
		String tipoDocument = customerEntity.getTipoDocument();
		String document = customerEntity.getDocument();
		String firstName = customerEntity.getFirstName();
		String lastName = customerEntity.getLastName();
		String phone = customerEntity.getPhone();
		String email = customerEntity.getEmail();
		LocalDate birthdate = customerEntity.getBirthdate();
		return new CustomerDTO(customerId, tipoDocument, document, firstName, lastName, phone, email, birthdate);
	}
	
	public GetReservasDTO toDTOBooking(ReservationEntity reservationEntity) {
		LocalDate dateReserva = reservationEntity.getDateReserva();
		String tipoReserva = reservationEntity.getTipoReserva();
        LocalTime hourStart = reservationEntity.getHourStart();
        LocalTime hourFinish = reservationEntity.getHourFinish();
        short quantityPersons = reservationEntity.getQuantityPersons();
        String observations = reservationEntity.getObservations();
        String statusReserva = reservationEntity.getStatusReserva();
		return new GetReservasDTO(dateReserva, tipoReserva, hourStart, hourFinish, quantityPersons, observations, statusReserva);
	}
}
