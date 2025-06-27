package com.gstech.controle_assinatura.DTO;

import java.util.UUID;

public record PlanReportDTO(

        UUID planId,
        String name,
        long activeSubscriptions

) {}
