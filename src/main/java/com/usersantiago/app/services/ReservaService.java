package com.usersantiago.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.usersantiago.app.persistence.entities.ReservationEntity;
import com.usersantiago.app.persistence.repositories.ReservasRepository;
import com.usersantiago.app.services.models.dtos.ReservationCreationDTO;

@Service
public class ReservaService {

  private ReservasRepository reservaRepository;
  private IReservationDAO bookingDAO;

  public ReservaService(ReservasRepository reservaRepository, IReservationDAO bookingDAO) {
    this.reservaRepository = reservaRepository;
    this.bookingDAO = bookingDAO;
  }

  public void createReserva(ReservationCreationDTO reservaRequest) {
    ReservationEntity newReservation = new ReservationEntity(
        reservaRequest.dateReserva(),
        reservaRequest.tipoReserva(),
        reservaRequest.hourStart(),
        reservaRequest.hourFinish(),
        reservaRequest.quantityPersons(),
        reservaRequest.observations(),
        reservaRequest.statusReserva(),
        reservaRequest.idCustomer());
    reservaRepository.saveReserva(newReservation);
  }

  public List<ReservationEntity> getBookings(Integer id) {
    return bookingDAO.findReservasById(id);
  }
}
