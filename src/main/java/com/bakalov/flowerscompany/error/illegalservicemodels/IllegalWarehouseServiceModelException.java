package com.bakalov.flowerscompany.error.illegalservicemodels;

import com.bakalov.flowerscompany.error.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Illegal warehouse service model")
public class IllegalWarehouseServiceModelException extends BaseException {

  public IllegalWarehouseServiceModelException(String message) {
    super(message, HttpStatus.NOT_ACCEPTABLE.value());
  }
}
