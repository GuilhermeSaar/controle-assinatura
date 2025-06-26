package com.gstech.controle_assinatura.enums;

public enum EventType {

    SUBSCRIPTION_CREATED("SUBSCRIPTION_CREATED"),
    PAYMENT_SUCCESS("PAYMENT_SUCCESS"),
    PAYMENT_FAILED("PAYMENT_FAILED");

    private final String status;

    EventType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
