package pl.wuniszewski.starwarsreport.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wuniszewski.starwarsreport.integration.service.IntegrationService;
import pl.wuniszewski.starwarsreport.report.repository.ReportRepository;

@Service
public class ReportGeneratorService {
    private ReportRepository reportRepository;
    private IntegrationService integrationService;

    @Autowired
    public ReportGeneratorService(ReportRepository reportRepository, IntegrationService integrationService) {
        this.reportRepository = reportRepository;
        this.integrationService = integrationService;
    }

}
