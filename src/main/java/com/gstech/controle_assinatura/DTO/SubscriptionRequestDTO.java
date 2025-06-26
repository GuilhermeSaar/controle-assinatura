package com.gstech.controle_assinatura.DTO;

import java.util.UUID;

public record SubscriptionRequestDTO(
        UUID plan_id,
        String customerEmail
        )
{}
