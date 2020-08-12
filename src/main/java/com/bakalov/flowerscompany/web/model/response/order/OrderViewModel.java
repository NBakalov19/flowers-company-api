package com.bakalov.flowerscompany.web.model.response.order;

import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderViewModel extends BaseViewModel {

  private String customer;
  private String variety;
  private Integer quantity;
  private Integer bunchesPerTray;
  private LocalDateTime orderDate;
  private LocalDateTime finishedOn;
  private String status;
}
