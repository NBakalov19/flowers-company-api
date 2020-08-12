package com.bakalov.flowerscompany.web.model.response.warehouse;

import com.bakalov.flowerscompany.web.model.response.flowersbatch.FlowersBatchViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseViewModel {

  private String name;
  private Integer maxCapacity;
  private Integer currCapacity;
  private Double temperature;
  private Set<FlowersBatchViewModel> batches;
}
