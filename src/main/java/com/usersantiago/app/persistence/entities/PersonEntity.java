package com.usersantiago.app.persistence.entities;

import java.time.LocalDate;

public class PersonEntity {
	private String documentType;
	private String documentNumber;
	private String firstName;
	private String lastName;
	private LocalDate birthdate;
	private String phone;
	private String email;
	private String password_cust;

	public PersonEntity() {
	}

	public PersonEntity(
			String documentType,
			String documentNumber,
			String firstName,
			String lastName,
			String phone,
			String email,
			String password,
			LocalDate birthdate) {
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.password_cust = password;
		this.birthdate = birthdate;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String tipoDocument) {
		this.documentType = tipoDocument;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String document) {
		this.documentNumber = document;
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

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
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
		return password_cust;
	}

	public void setPassword(String password) {
		this.password_cust = password;
	}

}
