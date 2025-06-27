package com.gstech.controle_assinatura.service;


import com.gstech.controle_assinatura.DTO.SubscriptionRequestDTO;
import com.gstech.controle_assinatura.DTO.SubscriptionResponseDTO;
import com.gstech.controle_assinatura.entities.Plan;
import com.gstech.controle_assinatura.entities.Subscription;
import com.gstech.controle_assinatura.enums.BillingCycle;
import com.gstech.controle_assinatura.enums.SubscriptionStatus;
import com.gstech.controle_assinatura.producer.SubscriptionEventPublisher;
import com.gstech.controle_assinatura.repository.PlanRepository;
import com.gstech.controle_assinatura.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class SubscriptionService {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionEventPublisher eventPublisher;


    public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO data) {

        Plan plan = planRepository.findById(data.plan_id())
                .orElseThrow(() -> new RuntimeException("Plano nao encontrado"));


        var subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
        subscription.setPlan(plan);
        subscription.setCustomerEmail(data.customerEmail());
        subscription.setSubscriptionStatus(SubscriptionStatus.PENDING);

        // define data de cobranca do plano
        LocalDate nextBillingDate;

        if (plan.getBillingCycle().equals(BillingCycle.ANUAL)) {

            nextBillingDate = LocalDate.now().plusYears(1);
        } else nextBillingDate = LocalDate.now().plusMonths(1);


        subscription.setNextBillingDate(nextBillingDate);

        subscriptionRepository.save(subscription);

        // publica evento
        eventPublisher.publisherSubscriptionCreatedEvent(subscription);

        return new SubscriptionResponseDTO(
                subscription.getId(),
                subscription.getSubscriptionStatus(),
                subscription.getNextBillingDate()
        );

    }


}
