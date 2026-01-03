package com.usersantiago.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_MODIFIED)

public class RequestNotModified extends RuntimeException {
  public RequestNotModified(String message) {
      super(message);
  }
}
