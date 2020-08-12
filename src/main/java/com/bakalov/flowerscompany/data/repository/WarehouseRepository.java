package com.bakalov.flowerscompany.data.repository;

import com.bakalov.flowerscompany.data.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {

  Warehouse findFirstByOrderByCurrCapacityAsc();

  @Override
  @Query("select w from Warehouse as w order by w.maxCapacity desc")
  List<Warehouse> findAll();

  Optional<Warehouse> findByName(String name);
}
