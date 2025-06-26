package com.gstech.controle_assinatura.enums;

public enum SubscriptionStatus {

    PENDENTE("PENDENTE"),
    ATIVA("ATIVA");

    private final String status;

    SubscriptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
