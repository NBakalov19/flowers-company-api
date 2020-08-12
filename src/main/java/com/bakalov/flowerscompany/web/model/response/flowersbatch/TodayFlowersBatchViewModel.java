package com.bakalov.flowerscompany.web.model.response.flowersbatch;

import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodayFlowersBatchViewModel extends BaseViewModel {

  private String variety;
  private String teamSupervisor;
  private String fieldName;
  private Integer trays;
  private Integer bunchesPerTray;
  private String warehouse;
}
