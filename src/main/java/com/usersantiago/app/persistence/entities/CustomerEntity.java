package com.usersantiago.app.persistence.entities;

import java.util.Date;

public class CustomerEntity extends PersonEntity {

	private Integer idCustomer;
	private byte active;
	private Date createdAt;
	private Date updatedAt;
	private int rol;

	public CustomerEntity() {
		super();
	}

	public CustomerEntity(Integer customer_id, String email, String pass) {
	}

	public CustomerEntity(Integer idCustomer, byte active, Date createdAt, Date updatedAt, int rol) {
		this.idCustomer = idCustomer;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.rol = rol;
	}

	public CustomerEntity(String tipoDocument, String document, String firstName, String lastName, String phone,
			String email, String password, Date birthdate) {
	    super(tipoDocument, document, firstName, lastName, phone, email, password, birthdate);
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public byte getActive() {
		return active;
	}

	public Date getCreatedAt(Date date) {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
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

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

}
