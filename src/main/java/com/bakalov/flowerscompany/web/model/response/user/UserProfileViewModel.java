package com.bakalov.flowerscompany.web.model.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileViewModel {

  private String username;
  private String email;
  private String profilePictureUrl;
}
