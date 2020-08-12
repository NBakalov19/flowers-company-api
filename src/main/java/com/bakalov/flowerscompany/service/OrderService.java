package com.bakalov.flowerscompany.service;

import com.bakalov.flowerscompany.service.model.OrderServiceModel;

import java.util.List;

public interface OrderService {
  OrderServiceModel makeOrder(OrderServiceModel serviceModel, String username);

  OrderServiceModel editOrder(String id, OrderServiceModel updateModel);

  OrderServiceModel findOrderById(String id);

  List<OrderServiceModel> findAllOrders();

  List<OrderServiceModel> findAllMyOrders(String customerName);

  void cancelOrder(String id);

  void reviewOrder(String id, String currentUser);
}
