package com.gstech.controle_assinatura.DTO;

import java.util.List;

public record SubscriptionReportDTO(

        long totalActive,
        long totalCancelled,
        List<PlanReportDTO> plans
) {}
