package com.usersantiago.app.services;

import java.util.List;

import com.usersantiago.app.persistence.entities.ReservationEntity;

public interface IReservationDAO {
	List<ReservationEntity> findReservasById(Integer id);
}
