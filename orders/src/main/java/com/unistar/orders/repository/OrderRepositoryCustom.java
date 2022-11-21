package com.unistar.orders.repository;

import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.model.OrderType;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderEntity> findOrderByOrderTypeAndDueDate(OrderType orderType, LocalDate dueDate);
}
