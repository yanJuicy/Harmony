package com.sparta.harmony.order.entity;

public enum OrderStatusEnum {

    PENDING(Status.PENDING),
    PREPARING(Status.PREPARING),
    DELIVERING(Status.DELIVERING),
    DELIVERED(Status.DELIVERED),
    CANCELED(Status.CANCELED);

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public String getDescription() {
        return status;
    }

    public static class Status {
        public static final String PENDING = "대기중";
        public static final String PREPARING = "음식 준비중";
        public static final String DELIVERING = "배달중";
        public static final String DELIVERED = "배달 완료";
        public static final String CANCELED = "주문 취소";
    }
}
