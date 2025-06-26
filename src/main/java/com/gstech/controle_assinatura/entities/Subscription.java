package com.gstech.controle_assinatura.entities;

import com.gstech.controle_assinatura.enums.SubscriptionStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_subscriptions")
public class Subscription {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;
    @Column(nullable = false)
    private String customerEmail;
    @Column(nullable = false)
    private SubscriptionStatus subscriptionStatusEnum;
    @Column(nullable = false)
    private LocalDate nextBillingDate;

    public Subscription(UUID id, Plan plan, String customerEmail, SubscriptionStatus subscriptionStatusEnum, LocalDate nextBillingDate) {
        this.id = id;
        this.plan = plan;
        this.customerEmail = customerEmail;
        this.subscriptionStatusEnum = subscriptionStatusEnum;
        this.nextBillingDate = nextBillingDate;
    }

    public Subscription() {
    }

    public UUID getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public SubscriptionStatus getSubscriptionStatusEnum() {
        return subscriptionStatusEnum;
    }

    public void setSubscriptionStatusEnum(SubscriptionStatus subscriptionStatusEnum) {
        this.subscriptionStatusEnum = subscriptionStatusEnum;
    }

    public LocalDate getNextBillingDate() {
        return nextBillingDate;
    }

    public void setNextBillingDate(LocalDate nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }
}
