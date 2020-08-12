package com.bakalov.flowerscompany.service.model;

import com.bakalov.flowerscompany.data.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderServiceModel extends BaseServiceModel {

  private UserServiceModel customer;
  private VarietyServiceModel variety;
  private Integer quantity;
  private Integer bunchesPerTray;
  private LocalDateTime orderDateTime;
  private LocalDateTime finishedOn;
  private Status status;
}
