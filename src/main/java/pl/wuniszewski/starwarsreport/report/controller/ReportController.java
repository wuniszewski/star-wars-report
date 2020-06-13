package pl.wuniszewski.starwarsreport.report.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wuniszewski.starwarsreport.report.converter.ReportConverter;
import pl.wuniszewski.starwarsreport.report.dto.ReportDto;
import pl.wuniszewski.starwarsreport.report.entity.Report;
import pl.wuniszewski.starwarsreport.report.service.ReportQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/report")
class ReportController {
    private ReportQueryService queryService;
    private ReportConverter reportConverter;

    @Autowired
    ReportController(ModelMapper modelMapper, ReportQueryService queryService, ReportConverter reportConverter) {
        this.queryService = queryService;
        this.reportConverter = reportConverter;
    }

    @GetMapping
    public List<ReportDto> getAllStoredReports() {
        return queryService.findAll().stream()
                .map(reportConverter::convertToDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/{reportId}")
    public ReportDto getById (@PathVariable(name = "reportId") Long id) {
        Report report = queryService.findById(id);
        return reportConverter.convertToDto(report);
    }

}
