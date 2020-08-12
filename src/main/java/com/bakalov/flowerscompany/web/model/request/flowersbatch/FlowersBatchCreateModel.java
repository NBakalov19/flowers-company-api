package com.bakalov.flowerscompany.web.model.request.flowersbatch;

import com.bakalov.flowerscompany.data.entity.Variety;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlowersBatchCreateModel {

  private String teamSupervisor;
  private String fieldName;
  private Variety variety;
  private Integer trays;
  private Integer bunchesPerTray;
  private String warehouse;
}
