package com.bakalov.flowerscompany.web.model.response.warehouse;

import com.bakalov.flowerscompany.service.model.FlowersBatchServiceModel;
import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseDetailsViewModel extends BaseViewModel {

  private String name;
  private Integer maxCapacity;
  private Integer currCapacity;
  private Double temperature;
  private Set<FlowersBatchServiceModel> batches;
}
