package com.gstech.controle_assinatura.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EventPayloadDTO(

        UUID id,
        String type,
        BigDecimal amount,
        LocalDate date
) {}
