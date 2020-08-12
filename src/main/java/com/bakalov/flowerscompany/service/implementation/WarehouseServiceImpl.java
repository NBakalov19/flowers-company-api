package com.bakalov.flowerscompany.service.implementation;

import com.bakalov.flowerscompany.data.entity.Warehouse;
import com.bakalov.flowerscompany.data.repository.WarehouseRepository;
import com.bakalov.flowerscompany.error.dublicates.WarehouseAlreadyExistException;
import com.bakalov.flowerscompany.error.notfound.WarehouseNotFoundException;
import com.bakalov.flowerscompany.error.unabled.NotPossibleToEmptyWarehouseException;
import com.bakalov.flowerscompany.service.WarehouseService;
import com.bakalov.flowerscompany.service.model.FlowersBatchServiceModel;
import com.bakalov.flowerscompany.service.model.WarehouseServiceModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.bakalov.flowerscompany.constant.WarehouseConstants.*;

@Service
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

  private final WarehouseRepository warehouseRepository;
  //  private final WarehouseServiceModelValidatorService validatorService;
  private final ModelMapper modelMapper;

  @Override
  public WarehouseServiceModel createWarehouse(WarehouseServiceModel serviceModel, String currentUser) {

    if (warehouseRepository.findByName(serviceModel.getName()).isPresent()) {
      throw new WarehouseAlreadyExistException(WAREHOUSE_ALLREADY_EXIST);
    }

    serviceModel.setTemperature(getRandomNumber());
    serviceModel.setBatches(new LinkedHashSet<>());
    serviceModel.setCurrCapacity(INITIAL_CURRENT_CAPACITY);

    Warehouse warehouse = modelMapper.map(serviceModel, Warehouse.class);
    warehouseRepository.saveAndFlush(warehouse);

    return modelMapper.map(warehouse, WarehouseServiceModel.class);
  }

  @Override
  public List<WarehouseServiceModel> findAllWarehouses() {

    return warehouseRepository.findAll()
            .stream()
            .map(warehouse -> modelMapper.map(warehouse, WarehouseServiceModel.class))
            .map(this::updateCurrCapacity)
            .collect(Collectors.toList());
  }

  @Override
  public Long getWarehousesCount() {
    return warehouseRepository.count();
  }

  @Override
  public WarehouseServiceModel findWarehouseById(String id) {
    return warehouseRepository.findById(id)
            .map(warehouse -> modelMapper.map(warehouse, WarehouseServiceModel.class))
            .orElseThrow(() -> new WarehouseNotFoundException(WAREHOUSE_NOT_FOUND));
  }

  @Override
  public WarehouseServiceModel editWarehouse(String warehouseId, WarehouseServiceModel updateModel, String currentUser) {

    WarehouseServiceModel serviceModel = findWarehouseById(warehouseId);
    serviceModel.setName(updateModel.getName());
    serviceModel.setMaxCapacity(updateModel.getMaxCapacity());

    Warehouse warehouse = modelMapper.map(serviceModel, Warehouse.class);
    warehouseRepository.saveAndFlush(warehouse);

    return modelMapper.map(warehouse, WarehouseServiceModel.class);
  }

  @Override
  public void deleteWarehouse(String id, String currentUser) {
    Warehouse warehouse = warehouseRepository.findById(id)
            .orElseThrow(() -> new WarehouseNotFoundException(WAREHOUSE_NOT_FOUND));

    warehouseRepository.delete(warehouse);
  }

  @Override
  public void emptyWarehouse(String id, String currentUser) {

    if (warehouseRepository.count() == 1) {
      throw new NotPossibleToEmptyWarehouseException(NOT_POSSIBLE_TO_EMPTY);
    }

    Warehouse warehouse = warehouseRepository.findById(id)
            .orElseThrow(() -> new WarehouseNotFoundException(WAREHOUSE_NOT_FOUND));

    Warehouse emptiestWarehouse = warehouseRepository.findFirstByOrderByCurrCapacityAsc();

    boolean isPossibleToMoveStock = isPossibleToMoveStock(
            warehouse.getCurrCapacity(),
            emptiestWarehouse.getCurrCapacity(),
            emptiestWarehouse.getMaxCapacity());

    if (isPossibleToMoveStock) {
      warehouse.getBatches().forEach(flowersBatch -> flowersBatch.setWarehouse(emptiestWarehouse));
      emptiestWarehouse.getBatches().addAll(warehouse.getBatches());
      warehouse.getBatches().clear();

      updateCurrCapacity(modelMapper.map(warehouse, WarehouseServiceModel.class));
      updateCurrCapacity(modelMapper.map(emptiestWarehouse, WarehouseServiceModel.class));
    } else {
      throw new NotPossibleToEmptyWarehouseException(NOT_POSSIBLE_TO_EMPTY);
    }
  }

  @Override
  public WarehouseServiceModel updateCurrCapacity(WarehouseServiceModel serviceModel) {

    int currCapacity = serviceModel.getBatches()
            .stream()
            .mapToInt(FlowersBatchServiceModel::getTrays)
            .sum();

    serviceModel.setCurrCapacity(currCapacity);

    Warehouse warehouse = modelMapper.map(serviceModel, Warehouse.class);

    warehouseRepository.saveAndFlush(warehouse);

    return modelMapper.map(warehouse, WarehouseServiceModel.class);
  }

  public boolean isPossibleToMoveStock(int firstWarehouseCurrCapacity,
                                       int secondWarehouseCurrCapacity,
                                       int secondWarehouseMaxCapacity) {
    return firstWarehouseCurrCapacity + secondWarehouseCurrCapacity <= secondWarehouseMaxCapacity;
  }

  public Double getRandomNumber() {
    return (Math.random()
            * ((MAX_WAREHOUSE_TEMPERATURE - MIN_WAREHOUSE_TEMPERATURE) + 1))
            + MIN_WAREHOUSE_TEMPERATURE;
  }
}
