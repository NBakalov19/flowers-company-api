package com.bakalov.flowerscompany.service;

import com.bakalov.flowerscompany.service.model.FlowersBatchServiceModel;
import com.bakalov.flowerscompany.service.model.VarietyServiceModel;
import com.bakalov.flowerscompany.web.model.request.flowersbatch.MoveBatchModel;

import java.util.List;

public interface FlowersBatchService {
  FlowersBatchServiceModel registerBatch(FlowersBatchServiceModel serviceModel, String currentUser);

  FlowersBatchServiceModel editFlowerBatch(String id, FlowersBatchServiceModel updateModel, String currentUser);

  FlowersBatchServiceModel findBatchById(String id);

  List<FlowersBatchServiceModel> findAllBatchesRegisteredToday();

  List<FlowersBatchServiceModel> findAllBatchesByVarietyAndBunchesPerTray(VarietyServiceModel varietyServiceModel, Integer bunchesPerTray);

  List<FlowersBatchServiceModel> findAllBatchesByBunchesPerTray(Integer bunchesPerTray);

  void moveBatch(String id, MoveBatchModel model, String currentUser);

  void deleteBatch(String id, String currentUser);
}
