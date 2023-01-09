package com.unistar.orders.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.exceptions.OrderServiceException;
import com.unistar.orders.model.OrderRequest;
import com.unistar.orders.model.OrderType;
import com.unistar.orders.repository.OrderRepository;
import com.unistar.orders.shared.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    private Utils utils;

    @BeforeEach
    void setUp() {
        utils = new Utils();
    }

    @Test
    void createOrder_successfully() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderType(OrderType.TV);
        orderRequest.setDueDate(utils.parseDate("2022/11/20"));
        orderRequest.setCustomerName("Joe");

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderRequest, orderEntity);
        orderEntity.setOrderNumber(2);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderEntity created = orderService.createOrder(orderRequest);

        assertNotNull(created);
        assertTrue(created.getOrderNumber() > 0);
        assertTrue(created.getCustomerName().equals(orderRequest.getCustomerName()));
        assertTrue(created.getDueDate().equals(orderRequest.getDueDate()));
        assertTrue(created.getOrderType().equals(orderRequest.getOrderType()));
    }

    @Test
    void createOrder_emptyType_throwException() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setDueDate(utils.parseDate("2022/11/20"));
        orderRequest.setCustomerName("Joe");

        assertThrows(OrderServiceException.class, () -> {
            OrderEntity created = orderService.createOrder(orderRequest);
        });
    }

    @Test
    void getOrders() {
        List<OrderEntity> orders = new LinkedList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderType(OrderType.TV);
        orderEntity.setDueDate(utils.parseDate("2022/11/20"));
        orderEntity.setCustomerName("Joe");
        orderEntity.setOrderNumber(2);
        orders.add(orderEntity);

        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setOrderType(OrderType.TV);
        orderEntity2.setDueDate(utils.parseDate("2022/11/23"));
        orderEntity2.setCustomerName("Mike");
        orderEntity2.setOrderNumber(3);
        orders.add(orderEntity2);

        when(orderRepository.findOrderByOrderTypeAndDueDate(any(OrderType.class), any(LocalDate.class))).thenReturn(orders);

        List<OrderEntity> orderList = orderService.getOrders(OrderType.TV, LocalDate.now());

        assertNotNull(orderList);
        assertEquals(orderList.size(), 2);
    }
}