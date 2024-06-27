package com.usersantiago.app.persistence.repositories;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import com.usersantiago.app.persistence.entities.ReservationEntity;
import com.usersantiago.app.services.IReservationDAO;
import com.usersantiago.app.services.models.dtos.ReservationsRowMapper;

@Repository
public class ReservasRepository implements IReservationDAO {
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert jdbcInsert;
  private static final String MY_TABLE = "reservations";
  private final ReservationsRowMapper mapper;

  public ReservasRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource, ReservationsRowMapper mapper ) {
    this.jdbcTemplate = jdbcTemplate;
    this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(MY_TABLE).usingGeneratedKeyColumns("reservation_id");
    this.mapper = mapper;
  }

  public Integer saveReserva(ReservationEntity reserva) {
    return jdbcInsert.executeAndReturnKey(
            new MapSqlParameterSource()
                    .addValue("date_reserva", reserva.getDateReserva())
                    .addValue("tipo_reserva", reserva.getTipoReserva())
                    .addValue("hour_start", reserva.getHourStart())
                    .addValue("hour_finish", reserva.getHourFinish())
                    .addValue("quantity_persons", reserva.getQuantityPersons())
                    .addValue("observations", reserva.getObservations())
                    .addValue("status_reserva", reserva.getStatusReserva())
                    .addValue("customer_id_reserva", reserva.getIdCustomer())
    ).intValue();
  }

@Override
  public List<ReservationEntity> findReservasById(Integer id) {
	  var queryGetReservations = "SELECT date_reserva, tipo_reserva, quantity_persons, observations, status_reserva,"
	  		+ "hour_start, hour_finish FROM " + MY_TABLE + " WHERE customer_id_reserva = :id";
      MapSqlParameterSource params = new MapSqlParameterSource();
      params.addValue("id", id);
      return jdbcTemplate.query(queryGetReservations, params, mapper);   	  	  
  }

}
