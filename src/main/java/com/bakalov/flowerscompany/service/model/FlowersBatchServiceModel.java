package com.bakalov.flowerscompany.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FlowersBatchServiceModel extends BaseServiceModel {

  private String teamSupervisor;
  private String fieldName;
  private VarietyServiceModel variety;
  private Integer trays;
  private Integer bunchesPerTray;
  private LocalDateTime datePicked;
  private WarehouseServiceModel warehouse;
}
