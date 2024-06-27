package com.usersantiago.app.services;

import java.util.List;
import java.util.Optional;

import com.usersantiago.app.persistence.entities.ReservationEntity;
import com.usersantiago.app.services.models.dtos.GetReservasDTO;

public interface IReservationDAO {
	List<ReservationEntity> findReservasById(Integer id);
}
