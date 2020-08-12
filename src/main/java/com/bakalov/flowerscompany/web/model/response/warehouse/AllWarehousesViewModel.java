package com.bakalov.flowerscompany.web.model.response.warehouse;

import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllWarehousesViewModel extends BaseViewModel {

  private String name;
  private Double temperature;
  private Integer currCapacity;
  private Integer maxCapacity;
}
