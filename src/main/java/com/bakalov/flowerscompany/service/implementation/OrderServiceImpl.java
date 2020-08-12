package com.bakalov.flowerscompany.service.implementation;

import com.bakalov.flowerscompany.data.entity.Order;
import com.bakalov.flowerscompany.data.entity.User;
import com.bakalov.flowerscompany.data.enums.Status;
import com.bakalov.flowerscompany.data.repository.OrderRepository;
import com.bakalov.flowerscompany.error.notfound.OrderNotFoundException;
import com.bakalov.flowerscompany.service.FlowersBatchService;
import com.bakalov.flowerscompany.service.OrderService;
import com.bakalov.flowerscompany.service.UserService;
import com.bakalov.flowerscompany.service.model.FlowersBatchServiceModel;
import com.bakalov.flowerscompany.service.model.OrderServiceModel;
import com.bakalov.flowerscompany.service.model.UserServiceModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bakalov.flowerscompany.constant.GlobalConstants.TODAY;
import static com.bakalov.flowerscompany.constant.OrderConstants.ORDER_NOT_FOUND;
import static com.bakalov.flowerscompany.data.enums.Status.APPROVED;
import static com.bakalov.flowerscompany.data.enums.Status.DENIED;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final UserService userService;
  private final FlowersBatchService flowersBatchService;
  //  private final OrderServiceModelValidatorService validatorService;
  private final ModelMapper modelMapper;

  @Override
  public OrderServiceModel makeOrder(OrderServiceModel serviceModel, String customerName) {

//    if (!validatorService.isValid(serviceModel)) {
//      throw new IllegalOrderServiceModelException(ORDER_BAD_CREDENTIALS);
//    }

    UserServiceModel customer = userService.findByUsername(customerName);

    serviceModel.setCustomer(customer);
    serviceModel.setOrderDateTime(TODAY);
    serviceModel.setStatus(Status.INPROGRESS);

    Order order = modelMapper.map(serviceModel, Order.class);
    orderRepository.saveAndFlush(order);

    return modelMapper.map(order, OrderServiceModel.class);
  }


  @Override
  public OrderServiceModel editOrder(String id, OrderServiceModel updateModel) {

    OrderServiceModel oldOrder = orderRepository.findById(id)
            .map(order -> modelMapper.map(order, OrderServiceModel.class))
            .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));

    oldOrder.setVariety(updateModel.getVariety());
    oldOrder.setQuantity(updateModel.getQuantity());
    oldOrder.setBunchesPerTray(updateModel.getBunchesPerTray());

    Order order = modelMapper.map(oldOrder, Order.class);
    orderRepository.saveAndFlush(order);

    return modelMapper.map(order, OrderServiceModel.class);
  }

  @Override
  public OrderServiceModel findOrderById(String id) {

    return orderRepository.findById(id)
            .map(order -> modelMapper.map(order, OrderServiceModel.class))
            .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
  }

  @Override
  public List<OrderServiceModel> findAllOrders() {

    return orderRepository.findAllByOrderByOrderDateTimeDescQuantityDesc()
            .stream()
            .map(order -> modelMapper.map(order, OrderServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public List<OrderServiceModel> findAllMyOrders(String username) {

    User customer = modelMapper.map(userService.findByUsername(username), User.class);

    return orderRepository.findAllByCustomerOrderByOrderDateTimeDescQuantityDesc(customer)
            .stream()
            .map(order -> modelMapper.map(order, OrderServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public void cancelOrder(String id) {

    Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
    orderRepository.delete(order);
  }

  @Override
  public void reviewOrder(String id, String currentUser) {

    OrderServiceModel order = findOrderById(id);

    List<FlowersBatchServiceModel> batches;
    if (order.getVariety() != null) {

      batches = flowersBatchService
              .findAllBatchesByVarietyAndBunchesPerTray(order.getVariety(), order.getBunchesPerTray());

    } else {
      batches = flowersBatchService.findAllBatchesByBunchesPerTray(order.getBunchesPerTray());

    }
    processOrder(order, batches, currentUser);
  }

  private void deniesOrder(OrderServiceModel order, String currentUser) {
    order.setStatus(DENIED);
    order.setFinishedOn(TODAY);
    orderRepository.saveAndFlush(modelMapper.map(order, Order.class));
    ;
  }

  private void approveOrder(OrderServiceModel order, String currentUser) {
    order.setStatus(APPROVED);
    order.setFinishedOn(TODAY);
    orderRepository.saveAndFlush(modelMapper.map(order, Order.class));
  }

  private void processOrder(OrderServiceModel order,
                            List<FlowersBatchServiceModel> batches,
                            String currentUser) {

    if (batches.isEmpty()) {
      deniesOrder(order, currentUser);
    } else {

      int batchesTotalTrays = batches.stream().mapToInt(FlowersBatchServiceModel::getTrays).sum();

      if (order.getQuantity() > batchesTotalTrays) {
        deniesOrder(order, currentUser);
      } else {
        int orderQuantity = order.getQuantity();

        for (FlowersBatchServiceModel batch : batches) {
          int currBatchTrays = batch.getTrays();

          if (orderQuantity > currBatchTrays) {
            flowersBatchService.deleteBatch(batch.getId(), currentUser);
            orderQuantity -= currBatchTrays;
          } else {
            if (orderQuantity < currBatchTrays) {
              int leftTrays = currBatchTrays - orderQuantity;
              batch.setTrays(leftTrays);
              flowersBatchService.editFlowerBatch(batch.getId(), batch, currentUser);
            } else {
              flowersBatchService.deleteBatch(batch.getId(), currentUser);
            }
            approveOrder(order, currentUser);
            break;
          }
        }
      }
    }
  }
}
