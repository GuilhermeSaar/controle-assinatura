package com.gstech.controle_assinatura.service;

import com.gstech.controle_assinatura.DTO.PlanReportDTO;
import com.gstech.controle_assinatura.DTO.SubscriptionReportDTO;
import com.gstech.controle_assinatura.enums.SubscriptionStatus;
import com.gstech.controle_assinatura.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionReportDTO generateReport() {

        List<Object[]> statusResults = subscriptionRepository.countByStatus();

        long totalActive = 0;
        long totalCancelled = 0;

        for (Object[] row : statusResults) {

            String status = (String) row[0];
            long count = ((Number) row[1]).longValue();

            if (status.equalsIgnoreCase(SubscriptionStatus.ATIVA.name())) {
                totalActive = count;
            } else if (status.equalsIgnoreCase(SubscriptionStatus.CANCELADA.name())) {
                totalCancelled = count;
            }
        }

        List<Object[]> plansResults = subscriptionRepository.countActiveSubscriptionsPerPlan();

        List<PlanReportDTO> plans = plansResults.stream()
                .map(row -> new PlanReportDTO(
                        UUID.fromString(row[0].toString()),
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();

        // 3. Monta o DTO final
        return new SubscriptionReportDTO(
                totalActive,
                totalCancelled,
                plans
        );
    }
}
