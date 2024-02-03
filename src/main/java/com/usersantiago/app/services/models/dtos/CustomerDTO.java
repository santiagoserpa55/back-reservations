package com.usersantiago.app.services.models.dtos;

import java.util.Date;

public class CustomerDTO {

	private String tipoDocument;
	private String document;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private Date birthdate;

	public CustomerDTO(String tipoDocument, String document, String firstName, String lastName, String phone,
			String email, Date birthdate) {
		this.tipoDocument = tipoDocument;
		this.document = document;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.birthdate = birthdate;
	}

	public String getTipoDocument() {
		return tipoDocument;
	}

	public String getDocument() {
		return document;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

}
