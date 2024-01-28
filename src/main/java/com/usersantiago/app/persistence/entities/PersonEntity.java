package com.usersantiago.app.persistence.entities;

import java.util.Date;

public class PersonEntity {
	private String tipoDocument;
	private String document;
	private String firstName;
	private String lastName;
	private Date birthdate;
	private String phone;
	private String email;
	private String password;

	public PersonEntity() {
	}

	public PersonEntity(String tipoDocument, String document, String firstName, String lastName, Date birthdate, String phone,
			String email, String password) {
		this.tipoDocument = tipoDocument;
		this.document = document;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}

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

	public Date getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(Date birthdate) {
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
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
