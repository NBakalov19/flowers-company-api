package com.bakalov.flowerscompany.service;

import com.bakalov.flowerscompany.service.model.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService extends UserDetailsService {

  UserServiceModel registerUser(UserServiceModel userServiceModel) throws RoleNotFoundException;

  UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

  UserServiceModel findByUsername(String username);

  List<UserServiceModel> findAllUsers();

  UserServiceModel setUserRole(String id, String role) throws RoleNotFoundException;
}
