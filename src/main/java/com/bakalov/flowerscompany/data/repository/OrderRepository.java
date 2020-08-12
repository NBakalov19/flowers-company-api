package com.bakalov.flowerscompany.data.repository;

import com.bakalov.flowerscompany.data.entity.Order;
import com.bakalov.flowerscompany.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
  List<Order> findAllByCustomerOrderByOrderDateTimeDescQuantityDesc(User customer);

  List<Order> findAllByOrderByOrderDateTimeDescQuantityDesc();
}
