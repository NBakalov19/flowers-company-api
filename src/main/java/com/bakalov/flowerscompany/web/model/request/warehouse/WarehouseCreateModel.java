package com.bakalov.flowerscompany.web.model.request.warehouse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseCreateModel {

  private String name;
  private Integer maxCapacity;
}
