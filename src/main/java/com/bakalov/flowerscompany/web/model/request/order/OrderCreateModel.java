package com.bakalov.flowerscompany.web.model.request.order;

import com.bakalov.flowerscompany.data.entity.Variety;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateModel {

  private Variety variety;
  private Integer quantity;
  private Integer bunchesPerTray;
}
