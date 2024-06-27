package com.usersantiago.app.services.models.dtos;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.usersantiago.app.persistence.entities.ReservationEntity;

@Component
public class ReservationsRowMapper implements RowMapper<ReservationEntity> {
	public ReservationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReservationEntity reservationEntity = new ReservationEntity();
		reservationEntity.setDateReserva(rs.getDate("date_reserva").toLocalDate());
		reservationEntity.setTipoReserva(rs.getString("tipo_reserva"));
		reservationEntity.setQuantityPersons(rs.getShort("quantity_persons"));
		reservationEntity.setObservations(rs.getString("observations"));
		reservationEntity.setStatusReserva(rs.getString("status_reserva"));
		reservationEntity.setHourStart(rs.getTime("hour_start").toLocalTime());
		reservationEntity.setHourFinish(rs.getTime("hour_finish").toLocalTime());
		return reservationEntity;
	}
}
