package com.unistar.orders.repository;

import com.unistar.orders.model.OrderType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderTypeConverter implements AttributeConverter<OrderType, String> {
    @Override
    public String convertToDatabaseColumn(OrderType orderType) {
        return orderType.toDbValue();
    }

    @Override
    public OrderType convertToEntityAttribute(String dbData) {
        return OrderType.from(dbData);
    }
}