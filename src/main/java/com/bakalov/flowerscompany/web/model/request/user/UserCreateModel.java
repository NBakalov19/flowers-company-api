package com.bakalov.flowerscompany.web.model.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateModel {

  private String username;
  private String password;
  private String confirmPassword;
  private MultipartFile image;
  private String email;
}
