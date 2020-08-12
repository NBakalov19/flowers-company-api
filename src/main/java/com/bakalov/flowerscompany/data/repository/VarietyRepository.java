package com.bakalov.flowerscompany.data.repository;

import com.bakalov.flowerscompany.data.entity.Variety;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarietyRepository extends JpaRepository<Variety, String> {
}
