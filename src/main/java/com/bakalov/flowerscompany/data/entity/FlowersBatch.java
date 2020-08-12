package com.bakalov.flowerscompany.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "flowers_batches")
@Getter
@Setter
@NoArgsConstructor
public class FlowersBatch extends BaseEntity {

  @Size(min = 3, max = 20)
  @Column(name = "team_supervisor", nullable = false)
  private String teamSupervisor;

  @Size(min = 3, max = 20)
  @Column(name = "field_name", nullable = false)
  private String fieldName;

  @ManyToOne(targetEntity = Variety.class)
  @JoinColumn(name = "variety_id", referencedColumnName = "id", nullable = false)
  private Variety variety;

  @Min(1)
  @Max(1000)
  @Column(name = "trays", nullable = false)
  private Integer trays;

  @Min(10)
  @Max(25)
  @Column(name = "bunches_per_tray", nullable = false)
  private Integer bunchesPerTray;

  @Column(name = "date_picked", nullable = false)
  private LocalDateTime datePicked;

  @ManyToOne(targetEntity = Warehouse.class)
  @JoinColumn(name = "warehouse_id", referencedColumnName = "id", nullable = false)
  private Warehouse warehouse;
}
