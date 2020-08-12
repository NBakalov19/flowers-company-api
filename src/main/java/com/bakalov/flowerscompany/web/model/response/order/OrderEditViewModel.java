package com.bakalov.flowerscompany.web.model.response.order;

import com.bakalov.flowerscompany.data.entity.Variety;
import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderEditViewModel extends BaseViewModel {

  private Variety variety;
  private Integer quantity;
  private Integer bunchesPerTray;
}
