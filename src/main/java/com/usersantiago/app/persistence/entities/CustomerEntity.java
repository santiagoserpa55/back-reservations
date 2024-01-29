package com.usersantiago.app.persistence.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

public class CustomerEntity extends PersonEntity {

	private Integer idCustomer;
	private byte active;
	private Date createdAt;
	private Date updatedAt;
	private int rol;

	public CustomerEntity() {
	}

	public CustomerEntity(Integer idCustomer, byte active, Date createdAt, Date updatedAt, int rol) {
		this.idCustomer = idCustomer;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.rol = rol;
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
