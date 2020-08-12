package com.bakalov.flowerscompany.web.model.response.warehouse;

import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseUpdateViewModel extends BaseViewModel {

  private String name;
  private Integer maxCapacity;
  private Integer currCapacity;
  private Double temperature;
}
