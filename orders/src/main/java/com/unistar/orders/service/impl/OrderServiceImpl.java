package com.unistar.orders.service.impl;

import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.exceptions.OrderServiceException;
import com.unistar.orders.model.ErrorMessages;
import com.unistar.orders.model.OrderRequest;
import com.unistar.orders.model.OrderType;
import com.unistar.orders.repository.OrderRepository;
import com.unistar.orders.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderEntity createOrder(OrderRequest order) {
        if (order.getDueDate() == null || order.getOrderType() == null ||
                order.getCustomerName() == null || order.getCustomerName().isEmpty())
            throw new OrderServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        return orderRepository.save(orderEntity);
    }

    @Override
    public List<OrderEntity> getOrders(OrderType orderType, LocalDate dueDate) {
        return orderRepository.findOrderByOrderTypeAndDueDate(orderType, dueDate);
    }

}
