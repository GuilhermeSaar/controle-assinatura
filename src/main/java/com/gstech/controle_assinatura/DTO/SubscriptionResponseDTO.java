package com.gstech.controle_assinatura.DTO;

import com.gstech.controle_assinatura.enums.SubscriptionStatus;

import java.time.LocalDate;
import java.util.UUID;

public record SubscriptionResponseDTO(

        UUID subscriptionId,
        SubscriptionStatus status,
        LocalDate nextBillingDate

)
{}
