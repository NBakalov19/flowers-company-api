package com.bakalov.flowerscompany.web.model.response.order;

import com.bakalov.flowerscompany.data.entity.Variety;
import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderDeleteViewModel extends BaseViewModel {

  private String customer;
  private Variety variety;
  private Integer quantity;
  private Integer bunchesPerTray;
  private LocalDateTime orderDate;
}
