package com.bakalov.flowerscompany.data.repository;

import com.bakalov.flowerscompany.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

  Optional<Role> findByAuthority(String authority);
}
