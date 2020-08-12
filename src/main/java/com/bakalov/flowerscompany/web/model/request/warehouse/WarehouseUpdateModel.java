package com.bakalov.flowerscompany.web.model.request.warehouse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseUpdateModel {

  private String id;
  private String name;
  private Integer maxCapacity;
  private Integer currCapacity;
  private Double temperature;
}
