package com.sparta.harmony.order.entity;

public enum OrderTypeEnum {
    TAKEOUT(OrderType.TAKEOUT),
    DELIVERY(OrderType.DELIVERY);

    private final String orderType;

    OrderTypeEnum(String orderType) {
        this.orderType = orderType;
    }

    public String getDescription() {
        return orderType;
    }

    public static class OrderType {
        public static final String TAKEOUT = "포장";
        public static final String DELIVERY = "배달";
    }
}
