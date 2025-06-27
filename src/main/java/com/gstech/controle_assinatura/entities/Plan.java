package com.gstech.controle_assinatura.entities;

import com.gstech.controle_assinatura.enums.BillingCycle;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_plans")
public class Plan {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "billing_cycle", nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;


    @OneToMany(mappedBy = "plan")
    private List<Subscription> subscriptions;


    public Plan(UUID id, String name, BigDecimal price, BillingCycle billingCycle) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.billingCycle = billingCycle;
    }

    public Plan() {

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }
}
