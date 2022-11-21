package com.unistar.orders.service;

import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.model.OrderRequest;
import com.unistar.orders.model.OrderType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService{
    OrderEntity createOrder(OrderRequest order);
    List<OrderEntity> getOrders(OrderType orderType, LocalDate dueDate);
}
