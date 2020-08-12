package com.bakalov.flowerscompany.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "varieties")
public class Variety extends BaseEntity {

  @Column(name = "variety", nullable = false)
  private String variety;
}
