package com.unistar.orders.model;

import java.time.LocalDate;

public class OrderRequest {
    private OrderType orderType;
    private LocalDate dueDate;
    private String customerName;

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return String.format("[OrderType : %s , Date: %s, customerName: %s]",
                this.orderType, this.dueDate.toString(), this.customerName);
    }
}
