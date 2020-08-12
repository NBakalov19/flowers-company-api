package com.bakalov.flowerscompany.web.model.response.user;

import com.bakalov.flowerscompany.web.model.response.BaseViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserViewModel extends BaseViewModel {

  private String profilePictureUrl;
  private String username;
  private String email;
  private Set<String> authorities;
}
