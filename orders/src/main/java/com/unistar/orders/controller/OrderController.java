package com.unistar.orders.controller;

import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.exceptions.OrderServiceException;
import com.unistar.orders.model.ErrorMessages;
import com.unistar.orders.model.OrderRequest;
import com.unistar.orders.model.OrderType;
import com.unistar.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @GetMapping(path="/orders",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OrderEntity>> getOrders(
            @RequestParam(value = "type", defaultValue = "") OrderType orderType,
            @RequestParam(value = "duedate", defaultValue = "") LocalDate dueDate) {

		logger.info("getOrders: type={}, duedate={} ...", orderType, dueDate);

        List<OrderEntity> orders = orderService.getOrders(orderType, dueDate);
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @PostMapping(path="/orders",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderRequest orderDetails) throws Exception {
		logger.info("createOrder : {} ", orderDetails.toString());
		
        if (orderDetails.getDueDate() == null || orderDetails.getOrderType() == null ||
                orderDetails.getCustomerName() == null || orderDetails.getCustomerName().isEmpty())
            throw new OrderServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        OrderEntity order = orderService.createOrder(orderDetails);
        return new ResponseEntity(order, HttpStatus.OK);
    }
}
