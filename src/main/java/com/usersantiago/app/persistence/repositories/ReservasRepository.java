package com.usersantiago.app.persistence.repositories;


import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.usersantiago.app.persistence.entities.ReservationEntity;

@Repository
public class ReservasRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final String TABLE = "reservations";

	public ReservasRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE).usingGeneratedKeyColumns("reservation_id");
	}
	
	
	public Integer saveReserva(ReservationEntity reserva) {
		return jdbcInsert.executeAndReturnKey(
				new MapSqlParameterSource()
				.addValue("date_reserva", reserva.getDateReserva())
				.addValue("tipo_reserva", reserva.getTipoReserva())
				.addValue("quantity_persons", reserva.getQuantityPersons())
				.addValue("observations", reserva.getObservations())
				.addValue("status_reserv", reserva.getStatusReserva())
				.addValue("customer_id_reserva", reserva.getIdCustomer())
				.addValue("active", 1)
				.addValue("created_at", new Date()).addValue("updated_at", new Date())
				).intValue();
	}
	
}
