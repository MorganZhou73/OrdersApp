package com.unistar.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unistar.orders.entity.OrderEntity;
import com.unistar.orders.model.OrderRequest;
import com.unistar.orders.model.OrderType;
import com.unistar.orders.service.OrderService;
import com.unistar.orders.shared.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    private Utils utils;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() throws Exception {
        utils = new Utils();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getOrders() throws Exception {
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

        when(orderService.getOrders(any(OrderType.class), any(LocalDate.class))).thenReturn(orders);
        MockHttpServletResponse response = mockMvc.perform(get("/v1/orders?type=TV")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orders)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void shouldCreateOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderType(OrderType.TV);
        orderRequest.setDueDate(utils.parseDate("2022/11/20"));
        orderRequest.setCustomerName("Joe");

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderRequest, orderEntity);
        orderEntity.setOrderNumber(2);

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(orderEntity);

        mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber", notNullValue()))
                .andExpect(jsonPath("$.orderType").value(orderRequest.getOrderType().toString()))
                .andExpect(jsonPath("$.dueDate").value(orderRequest.getDueDate().toString()))
                .andExpect(jsonPath("$.customerName").value(orderRequest.getCustomerName()));

    }
}