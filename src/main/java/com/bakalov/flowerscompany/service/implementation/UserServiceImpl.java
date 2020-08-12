package com.bakalov.flowerscompany.service.implementation;

import com.bakalov.flowerscompany.data.entity.User;
import com.bakalov.flowerscompany.data.repository.UserRepository;
import com.bakalov.flowerscompany.error.WrongPasswordException;
import com.bakalov.flowerscompany.error.dublicates.UserAlreadyExistException;
import com.bakalov.flowerscompany.error.dublicates.UserWithThisEmailAlreadyExist;
import com.bakalov.flowerscompany.error.illegalservicemodels.IllegalUserServiceModelException;
import com.bakalov.flowerscompany.error.notfound.UserNotFoundException;
import com.bakalov.flowerscompany.service.RoleService;
import com.bakalov.flowerscompany.service.UserService;
import com.bakalov.flowerscompany.service.model.UserServiceModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.bakalov.flowerscompany.constant.RoleConstants.*;
import static com.bakalov.flowerscompany.constant.UserConstants.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
//  private final UserServiceModelValidatorService validatorService;


  @Override
  public UserServiceModel registerUser(UserServiceModel userServiceModel) throws RoleNotFoundException {

//    if (!validatorService.isValid(userServiceModel)) {
//      throw new IllegalUserServiceModelException(USER_BAD_CREDENTIALS);
//    }

    if (userRepository.findByUsername(userServiceModel.getUsername()).isPresent()) {
      throw new UserAlreadyExistException(
              String.format(USERNAME_ALLREADY_EXIST, userServiceModel.getUsername()));
    }

    if (userRepository.findByEmail(userServiceModel.getEmail()).isPresent()) {
      throw new UserWithThisEmailAlreadyExist(
              String.format(USER_WITH_EMAIL_ALLREADY_EXIST, userServiceModel.getEmail()));
    }

    if (userRepository.count() == 0) {
      roleService.seedRolesInDb();
      userServiceModel.setAuthorities(roleService.findAllRoles());
    } else {
      userServiceModel.setAuthorities(new LinkedHashSet<>());
      userServiceModel.getAuthorities()
              .add(roleService.findByAuthority(CUSTOMER));
    }

    User user = modelMapper.map(userServiceModel, User.class);
    user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
    userRepository.saveAndFlush(user);

    return modelMapper.map(user, UserServiceModel.class);
  }

  @Override
  public UserServiceModel editUserProfile(UserServiceModel serviceModel, String oldPassword) {

    User user = userRepository.findByUsername(serviceModel.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

    if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
      throw new WrongPasswordException(PASSWORDS_NOT_MATCH);
    }

    user.setPassword(serviceModel.getPassword() != null
            ? bCryptPasswordEncoder.encode(serviceModel.getPassword())
            : user.getPassword());

    if (userRepository.findByEmail(serviceModel.getEmail()).isPresent()) {
      throw new UserWithThisEmailAlreadyExist(USER_WITH_EMAIL_ALLREADY_EXIST);
    }

    user.setEmail(serviceModel.getEmail());

//    if (!validatorService.isValid(serviceModel)) {
//      throw new IllegalUserServiceModelException(USER_BAD_CREDENTIALS);
//    }

    userRepository.saveAndFlush(user);

    return modelMapper.map(user, UserServiceModel.class);
  }

  @Override
  public UserServiceModel findByUsername(String username) {
    return userRepository.findByUsername(username)
            .map(user -> modelMapper.map(user, UserServiceModel.class))
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
  }

  @Override
  public List<UserServiceModel> findAllUsers() {
    return userRepository.findAll()
            .stream()
            .map(user -> modelMapper.map(user, UserServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public UserServiceModel setUserRole(String id, String role) throws RoleNotFoundException {

    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);
    userServiceModel.getAuthorities().clear();

    if (role.equals("operator")) {
      userServiceModel.getAuthorities().add(roleService.findByAuthority(OPERATOR));
    } else if (role.equals("admin")) {
      userServiceModel.getAuthorities().add(roleService.findByAuthority(OPERATOR));
      userServiceModel.getAuthorities().add(roleService.findByAuthority(ADMIN));
    }

    User promotedUser = modelMapper.map(userServiceModel, User.class);
    userRepository.saveAndFlush(promotedUser);

    return modelMapper.map(promotedUser, UserServiceModel.class);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
  }
}
