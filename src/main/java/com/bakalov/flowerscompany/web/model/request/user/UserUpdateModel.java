package com.bakalov.flowerscompany.web.model.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateModel {

  private String username;
  private String oldPassword;
  private String password;
  private String confirmPassword;
  private String email;
}
