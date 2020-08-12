package com.bakalov.flowerscompany.error.notfound;

import com.bakalov.flowerscompany.error.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Order not found.")
public class OrderNotFoundException extends BaseException {

  public OrderNotFoundException(String message) {
    super(message, HttpStatus.BAD_REQUEST.value());
  }
}
