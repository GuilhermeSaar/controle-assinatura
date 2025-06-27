package com.gstech.controle_assinatura.controller;

import com.gstech.controle_assinatura.DTO.SubscriptionReportDTO;
import com.gstech.controle_assinatura.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/subscriptions")
    public ResponseEntity<SubscriptionReportDTO> getSubscriptionReport() {

        SubscriptionReportDTO report = reportService.generateReport();
        return ResponseEntity.ok(report);
    }
}
