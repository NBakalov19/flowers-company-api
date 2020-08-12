package com.bakalov.flowerscompany.service.implementation;

import com.bakalov.flowerscompany.data.entity.FlowersBatch;
import com.bakalov.flowerscompany.data.entity.Variety;
import com.bakalov.flowerscompany.data.repository.FlowersBatchRepository;
import com.bakalov.flowerscompany.error.notfound.FlowersBatchNotFoundException;
import com.bakalov.flowerscompany.error.unabled.NotPossibleToRegisterBatchException;
import com.bakalov.flowerscompany.service.FlowersBatchService;
import com.bakalov.flowerscompany.service.WarehouseService;
import com.bakalov.flowerscompany.service.model.FlowersBatchServiceModel;
import com.bakalov.flowerscompany.service.model.VarietyServiceModel;
import com.bakalov.flowerscompany.service.model.WarehouseServiceModel;
import com.bakalov.flowerscompany.web.model.request.flowersbatch.MoveBatchModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bakalov.flowerscompany.constant.FlowersBatchConstants.FLOWERS_BATCH_NOT_FOUND;
import static com.bakalov.flowerscompany.constant.FlowersBatchConstants.NOT_POSSIBLE_TO_REGISTER;
import static com.bakalov.flowerscompany.constant.GlobalConstants.*;

@Service
@AllArgsConstructor
public class FlowersBatchServiceImpl implements FlowersBatchService {

  private final FlowersBatchRepository flowersBatchRepository;
  //  private final FlowersBatchServiceModelValidatorService validatorService;
  private final WarehouseService warehouseService;
  private final ModelMapper modelMapper;

  @Override
  public FlowersBatchServiceModel registerBatch(FlowersBatchServiceModel serviceModel, String currentUser) {

//    if (!validatorService.isValid(serviceModel)) {
//      throw new IllegalFlowersBatchServiceModelException(FLOWERS_BATCH_BAD_CREDENTIALS);
//    }

    serviceModel.setDatePicked(TODAY);

    WarehouseServiceModel warehouseServiceModel = serviceModel.getWarehouse();

    hasRoomInWarehouse(warehouseServiceModel, serviceModel);

    FlowersBatch flowersBatch =
            modelMapper.map(serviceModel, FlowersBatch.class);

    flowersBatchRepository.saveAndFlush(flowersBatch);

    return modelMapper.map(flowersBatch, FlowersBatchServiceModel.class);
  }

  @Override
  public FlowersBatchServiceModel editFlowerBatch(String id,
                                                  FlowersBatchServiceModel updateModel,
                                                  String currentUser) {

//    if (!validatorService.isValid(updateModel)) {
//      throw new IllegalFlowersBatchServiceModelException(FLOWERS_BATCH_BAD_CREDENTIALS);
//    }

    FlowersBatchServiceModel serviceModel = findBatchById(id);

    hasRoomInWarehouse(serviceModel.getWarehouse(), updateModel);

    serviceModel.setTeamSupervisor(updateModel.getTeamSupervisor());
    serviceModel.setFieldName(updateModel.getFieldName());
    serviceModel.setVariety(updateModel.getVariety());
    serviceModel.setTrays(updateModel.getTrays());
    serviceModel.setBunchesPerTray(updateModel.getBunchesPerTray());

    FlowersBatch flowersBatch = modelMapper.map(serviceModel, FlowersBatch.class);

    flowersBatchRepository.saveAndFlush(flowersBatch);

    return modelMapper.map(flowersBatch, FlowersBatchServiceModel.class);
  }

  @Override
  public FlowersBatchServiceModel findBatchById(String id) {

    return flowersBatchRepository.findById(id)
            .map(flowersBatch -> modelMapper.map(flowersBatch, FlowersBatchServiceModel.class))
            .orElseThrow(() -> new FlowersBatchNotFoundException(FLOWERS_BATCH_NOT_FOUND));
  }


  @Override
  public List<FlowersBatchServiceModel> findAllBatchesRegisteredToday() {

    return flowersBatchRepository.findAllByDatePickedBetweenOrderByDatePickedAsc(BEGIN_OF_DAY, END_OF_DAY)
            .stream()
            .map(batch -> modelMapper.map(batch, FlowersBatchServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public List<FlowersBatchServiceModel> findAllBatchesByVarietyAndBunchesPerTray(VarietyServiceModel varietyServiceModel, Integer bunchesPerTray) {
    Variety variety = modelMapper.map(varietyServiceModel, Variety.class);

    return flowersBatchRepository
            .findAllByVarietyAndBunchesPerTrayOrderByTraysDesc(variety, bunchesPerTray)
            .stream()
            .map(batch -> modelMapper.map(batch, FlowersBatchServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public List<FlowersBatchServiceModel> findAllBatchesByBunchesPerTray(Integer bunchesPerTray) {
    return flowersBatchRepository.findAllByBunchesPerTrayOrderByTrays(bunchesPerTray)
            .stream()
            .map(batch -> modelMapper.map(batch, FlowersBatchServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public void moveBatch(String id, MoveBatchModel model, String currentUser) {

    FlowersBatchServiceModel flowersBatchServiceModel = findBatchById(id);

    WarehouseServiceModel oldWarehouse =
            warehouseService.findWarehouseById(flowersBatchServiceModel.getWarehouse().getId());

    WarehouseServiceModel newWarehouse =
            warehouseService.findWarehouseById(model.warehouse);

    hasRoomInWarehouse(newWarehouse, flowersBatchServiceModel);

    flowersBatchServiceModel.setWarehouse(newWarehouse);

    Set<FlowersBatchServiceModel> oldWarehouseNewBatches = oldWarehouse.getBatches()
            .stream()
            .filter(fb -> !fb.getId().equals(flowersBatchServiceModel.getId()))
            .collect(Collectors.toSet());

    oldWarehouse.setBatches(oldWarehouseNewBatches);
    newWarehouse.getBatches().add(flowersBatchServiceModel);

    warehouseService.updateCurrCapacity(oldWarehouse);
    warehouseService.updateCurrCapacity(newWarehouse);
  }

  @Override
  public void deleteBatch(String id, String currentUser) {
    flowersBatchRepository.deleteById(id);
  }

  public void hasRoomInWarehouse(WarehouseServiceModel warehouseServiceModel,
                                 FlowersBatchServiceModel flowersBatchServiceModel) {

    int currCapacity = warehouseServiceModel.getCurrCapacity();

    if (currCapacity + flowersBatchServiceModel.getTrays() > warehouseServiceModel.getMaxCapacity()) {
      throw new NotPossibleToRegisterBatchException(
              String.format(NOT_POSSIBLE_TO_REGISTER, warehouseServiceModel.getName()));
    }
  }
}
