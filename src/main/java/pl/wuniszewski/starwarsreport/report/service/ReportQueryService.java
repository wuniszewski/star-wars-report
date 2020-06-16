package pl.wuniszewski.starwarsreport.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wuniszewski.starwarsreport.report.entity.Report;
import pl.wuniszewski.starwarsreport.report.exception.NotExistingResourceException;
import pl.wuniszewski.starwarsreport.report.repository.ReportRepository;

import java.util.List;

@Service
public class ReportQueryService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportQueryService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(() ->
                new NotExistingResourceException("No resource found with ID: " + id));
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
