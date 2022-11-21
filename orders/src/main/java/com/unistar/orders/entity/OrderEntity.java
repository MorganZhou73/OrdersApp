package com.unistar.orders.entity;

import com.unistar.orders.model.OrderType;
import com.unistar.orders.repository.OrderTypeConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="items")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderNumber;

    @Convert(converter = OrderTypeConverter.class)
    private OrderType orderType;

    @Column(nullable = false)
    private LocalDate dueDate;
	
	@Column(nullable = false, length = 50)
	private String customerName;

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

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
}
