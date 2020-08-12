package com.bakalov.flowerscompany.service;

import com.bakalov.flowerscompany.service.model.RoleServiceModel;

import javax.management.relation.RoleNotFoundException;
import java.util.Set;

public interface RoleService {

  RoleServiceModel findByAuthority(String authority) throws RoleNotFoundException;

  Set<RoleServiceModel> findAllRoles();

  void seedRolesInDb();
}
