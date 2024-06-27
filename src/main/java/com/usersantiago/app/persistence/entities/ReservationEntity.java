package com.usersantiago.app.persistence.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationEntity extends CustomerEntity {
	
	private Integer idReserva;
	private Integer idCustomer;
	private LocalDate dateReserva;
	private LocalTime hourStart;
	private LocalTime hourFinish;
	private String tipoReserva;
	private short quantityPersons;
	private String observations;
	private String statusReserva;
	private byte active;
	private Instant createdAt;
	private Instant updatedAt;
	
	public ReservationEntity(Integer idReserva, Integer idCustomer, LocalDate dateReserva, String tipoReserva,
			short quantityPersons,String observations, String statusReserva, byte active, Instant createdAt, Instant updatedAt) {
		super();
		this.idReserva = idReserva;
		this.idCustomer = idCustomer;
		this.dateReserva = dateReserva;
		this.tipoReserva = tipoReserva;
		this.quantityPersons = quantityPersons;
		this.observations = observations;
		this.statusReserva = statusReserva;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ReservationEntity(LocalDate dateReserva, String tipoReserva, LocalTime hourStart, LocalTime hourFinish, short quantityPersons,
			String observations, String statusReserva, Integer idCustomer) {
		this.dateReserva = dateReserva;
		this.hourStart = hourStart;
		this.hourFinish = hourFinish;
		this.tipoReserva = tipoReserva;
		this.quantityPersons = quantityPersons;
		this.observations = observations;
		this.statusReserva = statusReserva;
		this.idCustomer = idCustomer;
	}

	public ReservationEntity() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public LocalDate getDateReserva() {
		return dateReserva;
	}

	public LocalTime getHourStart() { return hourStart; }

	public void setHourStart(LocalTime hourStart) { this.hourStart = hourStart; }

	public LocalTime getHourFinish() { return hourFinish; }

	public void setHourFinish(LocalTime hourFinish) { this.hourFinish = hourFinish; }
	public void setDateReserva(LocalDate dateReserva) {
		this.dateReserva = dateReserva;
	}

	public String getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(String tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public short getQuantityPersons() {
		return quantityPersons;
	}

	public void setQuantityPersons(short quantityPersons) {
		this.quantityPersons = quantityPersons;
	}

	public String getStatusReserva() {
		return statusReserva;
	}

	public void setStatusReserva(String statusReserva) {
		this.statusReserva = statusReserva;
	}

	public byte getActive() {
		return active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
}
