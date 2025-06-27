package com.gstech.controle_assinatura.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentWebhookDTO(

        UUID subscriptionId,
        String event,
        BigDecimal amount,
        LocalDate date
) {}
