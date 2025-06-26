package com.gstech.controle_assinatura.enums;

public enum BillingCycle {

    MENSAL("MENSAL"),
    ANUAL("ANUAL");

    private final String billingCycle;

    BillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }
    public String getBillingCycle() {
        return billingCycle;
    }

}
