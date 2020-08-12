package com.bakalov.flowerscompany.error.dublicates;

import com.bakalov.flowerscompany.error.BaseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate warehouse")
public class WarehouseAlreadyExistException extends BaseException {

  public WarehouseAlreadyExistException(String message) {
    super(message, HttpStatus.CONFLICT.value());
  }
}
