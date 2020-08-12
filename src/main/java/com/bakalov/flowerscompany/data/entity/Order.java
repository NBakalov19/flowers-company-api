package com.bakalov.flowerscompany.data.entity;

import com.bakalov.flowerscompany.data.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User customer;

  @ManyToOne(targetEntity = Variety.class)
  @JoinColumn(name = "variety_id", referencedColumnName = "id", nullable = false)
  private Variety variety;

  @Min(1)
  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Min(10)
  @Max(25)
  @Column(name = "bunches_per_tray", nullable = false)
  private Integer bunchesPerTray;

  @Column(name = "order_date_time", nullable = false, updatable = false)
  private LocalDateTime orderDateTime;

  @Column(name = "finished_on")
  private LocalDateTime finishedOn;

  @Column(name = "status", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Status status;
}
