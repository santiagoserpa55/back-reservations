package com.usersantiago.app.services.models.dtos;

import java.util.Date;

public class CustomerCreationDTO {

	private String tipoDocument;
	private String document;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String password;
	private Date birthdate;

	
	public CustomerCreationDTO() {}
	
	public String getTipoDocument() {
		return tipoDocument;
	}

	public void setTipoDocument(String tipoDocument) {
		this.tipoDocument = tipoDocument;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

}
