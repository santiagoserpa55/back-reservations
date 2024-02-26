package com.usersantiago.app.persistence.entities;

import java.time.Instant;
import java.time.LocalDateTime;

public class ReservationEntity extends CustomerEntity {
	
	private Integer idReserva;
	private Integer idCustomer;
	private LocalDateTime dateReserva;
	private String tipoReserva;
	private short quantityPersons;
	private String observations;
	private String statusReserva;
	private byte active;
	private Instant createdAt;
	private Instant updatedAt;
	
	
	public ReservationEntity(Integer idReserva, Integer idCustomer, LocalDateTime dateReserva, String tipoReserva,
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

	

	public ReservationEntity(LocalDateTime dateReserva, String tipoReserva, short quantityPersons,
			String observations, String statusReserva, Integer idCustomer) {
		this.dateReserva = dateReserva;
		this.tipoReserva = tipoReserva;
		this.quantityPersons = quantityPersons;
		this.observations = observations;
		this.statusReserva = statusReserva;
		this.idCustomer = idCustomer;
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


	public LocalDateTime getDateReserva() {
		return dateReserva;
	}


	public void setDateReserva(LocalDateTime dateReserva) {
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
