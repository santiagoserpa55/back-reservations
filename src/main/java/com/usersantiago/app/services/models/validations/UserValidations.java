package com.usersantiago.app.services.models.validations;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.models.dtos.ResponseDTO;

public class UserValidations {

	public ResponseDTO validate(CustomerEntity user) {
		ResponseDTO response = new ResponseDTO();

		response.setNumOfErrors(0);

		if (user.getFirstName() == null || user.getFirstName().length() < 3 || user.getFirstName().length() > 15) {
			response.setNumOfErrors(response.getNumOfErrors() + 1);
			response.setMessage("Verifique el campo firstName");
		}

		if (user.getLastName() == null || user.getLastName().length() < 3 || user.getLastName().length() > 30) {
			response.setNumOfErrors(response.getNumOfErrors() + 1);
			response.setMessage("Verifique el campo lastName");
		}

		if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			response.setNumOfErrors(response.getNumOfErrors() + 1);
			response.setMessage("Verifique el campo email");
		}

		if (user.getPassword() == null
				|| !user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
			response.setNumOfErrors(response.getNumOfErrors() + 1);
			response.setMessage(
					"El campo contraseña debe tener mas de 8 caracteres (Mayusculas, numeros y caracteres especiales)");
		}

		return response;
	}

}
