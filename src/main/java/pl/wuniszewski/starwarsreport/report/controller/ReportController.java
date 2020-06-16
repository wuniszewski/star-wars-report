package pl.wuniszewski.starwarsreport.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wuniszewski.starwarsreport.report.converter.ReportConverter;
import pl.wuniszewski.starwarsreport.report.dto.QueryCriteriaDto;
import pl.wuniszewski.starwarsreport.report.dto.ReportDto;
import pl.wuniszewski.starwarsreport.report.entity.Report;
import pl.wuniszewski.starwarsreport.report.exception.BadRequestValueException;
import pl.wuniszewski.starwarsreport.report.exception.NotExistingResourceException;
import pl.wuniszewski.starwarsreport.report.service.ReportGeneratorService;
import pl.wuniszewski.starwarsreport.report.service.ReportQueryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
class ReportController {

    private ReportQueryService queryService;
    private ReportGeneratorService generatorService;
    private ReportConverter reportConverter;

    @Autowired
    ReportController(ReportQueryService queryService, ReportGeneratorService generatorService, ReportConverter reportConverter) {
        this.queryService = queryService;
        this.generatorService = generatorService;
        this.reportConverter = reportConverter;
    }

    @GetMapping
    public List<ReportDto> getAllStoredReports() {
        return queryService.findAll().stream()
                .map(reportConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{reportId}")
    public ReportDto getById(@PathVariable(name = "reportId") Long id) {
        Report report = queryService.findById(id);
        return reportConverter.convertToDto(report);
    }

    @DeleteMapping("/{reportId}")
    public void deleteById(@PathVariable(name = "reportId") Long id) {
        if (id == null) {
            throw new BadRequestValueException("Id cannot be null.");
        }
        generatorService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        generatorService.deleteAll();
    }

    @PutMapping("/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void generateReport(@PathVariable(name = "reportId") Long id, @RequestBody QueryCriteriaDto criteria) {
        if (validateQueryCriteria(criteria)) {
            throw new BadRequestValueException("No query criteria given.");
        }
        try {
            generatorService.updateReport(queryService.findById(id), criteria);
        } catch (NotExistingResourceException e) {
            generatorService.createReport(id, criteria);
        }
    }

    private boolean validateQueryCriteria(QueryCriteriaDto queryCriteria) {
        return queryCriteria.getQueryCriteriaPlanetName() == null || queryCriteria.getQueryCriteriaCharacterPhrase() == null;
    }
}
