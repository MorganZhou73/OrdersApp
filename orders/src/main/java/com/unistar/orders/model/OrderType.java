package com.unistar.orders.model;

public enum OrderType {
    INTERNET, TV, BUNDLE;

    public String toDbValue() {
        return this.name().toLowerCase();
    }

    public static OrderType from(String orderType) {
        return OrderType.valueOf(orderType.toUpperCase());
    }
}
