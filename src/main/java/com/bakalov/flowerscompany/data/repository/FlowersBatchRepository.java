package com.bakalov.flowerscompany.data.repository;

import com.bakalov.flowerscompany.data.entity.FlowersBatch;
import com.bakalov.flowerscompany.data.entity.Variety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlowersBatchRepository extends JpaRepository<FlowersBatch, String> {

  List<FlowersBatch> findAllByDatePickedBetweenOrderByDatePickedAsc(LocalDateTime beginOfDay, LocalDateTime endOfDay);

  List<FlowersBatch> findAllByBunchesPerTrayOrderByTrays(Integer bunchesPerTray);

  List<FlowersBatch> findAllByVarietyAndBunchesPerTrayOrderByTraysDesc(Variety variety, Integer bunchesPerTray);

  @Modifying
  @Query("delete from FlowersBatch as f where f.id = :id")
  void deleteById(@Param("id") String id);
}
