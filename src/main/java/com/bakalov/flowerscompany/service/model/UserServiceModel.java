package com.bakalov.flowerscompany.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel{

  private String username;
  private String password;
  private String email;
  private String profilePictureUrl;
  private Set<RoleServiceModel> authorities;
}
