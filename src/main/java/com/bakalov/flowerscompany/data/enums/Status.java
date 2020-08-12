package com.bakalov.flowerscompany.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Status {

  INPROGRESS("In progress"),
  APPROVED("Approved"),
  DENIED("Denied");

  private final String status;

  public static Stream<Status> stream() {
    return Stream.of(Status.values());
  }
}
