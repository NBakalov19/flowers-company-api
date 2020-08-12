package com.bakalov.flowerscompany.config;

import com.bakalov.flowerscompany.service.model.FlowersBatchServiceModel;
import com.bakalov.flowerscompany.service.model.OrderServiceModel;
import com.bakalov.flowerscompany.service.model.UserServiceModel;
import com.bakalov.flowerscompany.service.model.WarehouseServiceModel;
import com.bakalov.flowerscompany.web.model.response.flowersbatch.FlowersBatchDeleteViewModel;
import com.bakalov.flowerscompany.web.model.response.flowersbatch.TodayFlowersBatchViewModel;
import com.bakalov.flowerscompany.web.model.response.order.OrderDeleteViewModel;
import com.bakalov.flowerscompany.web.model.response.order.OrderViewModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  private static final ModelMapper modelMapper;

  static {
    modelMapper = new ModelMapper();
    initModelMapper();
  }

  private static void initModelMapper() {

    Converter<WarehouseServiceModel, String> warehouseServiceModelStringConverter =
            context -> String.valueOf(context.getSource().getName());

    Converter<UserServiceModel, String> userServiceModelStringConverter =
            context -> String.valueOf(context.getSource().getUsername());

    ModelMapperConfiguration.modelMapper.createTypeMap(
            FlowersBatchServiceModel.class, TodayFlowersBatchViewModel.class)
            .addMappings(map ->
                    map.using(warehouseServiceModelStringConverter)
                            .map(
                                    FlowersBatchServiceModel::getWarehouse,
                                    TodayFlowersBatchViewModel::setWarehouse));

    ModelMapperConfiguration.modelMapper
            .createTypeMap(FlowersBatchServiceModel.class, FlowersBatchDeleteViewModel.class)
            .addMappings(map ->
                    map.using(warehouseServiceModelStringConverter)
                            .map(
                                    FlowersBatchServiceModel::getWarehouse,
                                    FlowersBatchDeleteViewModel::setWarehouse));

    ModelMapperConfiguration.modelMapper
            .createTypeMap(OrderServiceModel.class, OrderViewModel.class)
            .addMappings(map ->
                    map.using(userServiceModelStringConverter)
                            .map(
                                    OrderServiceModel::getCustomer,
                                    OrderViewModel::setCustomer
                            ));

    ModelMapperConfiguration.modelMapper
            .createTypeMap(OrderServiceModel.class, OrderDeleteViewModel.class)
            .addMappings(map ->
                    map.using(userServiceModelStringConverter)
                            .map(
                                    OrderServiceModel::getCustomer,
                                    OrderDeleteViewModel::setCustomer
                            ));
  }

  @Bean
  public ModelMapper modelMapper() {
    return modelMapper;
  }
}
