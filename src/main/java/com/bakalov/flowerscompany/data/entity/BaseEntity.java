package com.bakalov.flowerscompany.data.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(
          name = "uuid-string",
          strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false, unique = true)
  private String id;

}
