package com.usersantiago.app.persistence.entities;

import java.time.Instant;
import java.time.LocalDate;

public class CustomerEntity extends PersonEntity {

	private Integer idCustomer;
	private byte active;
	private Instant createdAt;
	private Instant updatedAt;
	private int rol;

	public CustomerEntity() {
		super();
	}

	public CustomerEntity(Integer customer_id, String email, String pass) {
	}

	public CustomerEntity(Integer idCustomer, byte active, Instant createdAt, Instant updatedAt, int rol) {
		this.idCustomer = idCustomer;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.rol = rol;
	}

	public CustomerEntity(String tipoDocument, String document, String firstName, String lastName, String phone,
			String email, String password, LocalDate birthdate) {
	    super(tipoDocument, document, firstName, lastName, phone, email, password, birthdate);
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public byte getActive() {
		return active;
	}

	public Instant getCreatedAt(Instant date) {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getRol() {
		return rol;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

}
