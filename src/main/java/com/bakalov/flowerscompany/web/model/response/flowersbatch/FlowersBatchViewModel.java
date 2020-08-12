package com.bakalov.flowerscompany.web.model.response.flowersbatch;

import com.bakalov.flowerscompany.data.entity.Variety;
import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlowersBatchViewModel extends BaseViewModel {

  private String teamSupervisor;
  private String fieldName;
  private Variety variety;
  private Integer trays;
  private Integer bunchesPerTray;
  private String warehouse;
}
