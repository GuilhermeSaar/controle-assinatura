package com.gstech.controle_assinatura.enums;

public enum SubscriptionStatus {

    PENDING("PENDENTE"),
    ACTIVE("ATIVA");

    private final String status;

    SubscriptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
