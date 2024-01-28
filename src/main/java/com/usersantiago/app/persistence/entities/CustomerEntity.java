package com.usersantiago.app.persistence.entities;

import java.util.Date;

public class CustomerEntity extends PersonEntity {

	private Integer idCustomer;
	private byte active;
	private Date createdAt;
	private Date updatedAt;

	public CustomerEntity() {
		super();
	}

	public CustomerEntity(Integer idCustomer, byte active, Date createdAt, Date updatedAt) {
		super();
		this.idCustomer = idCustomer;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public byte getActive() {
		return active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
