package pl.wuniszewski.starwarsreport.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.integration.service.IntegrationService;
import pl.wuniszewski.starwarsreport.report.dto.QueryCriteriaDto;
import pl.wuniszewski.starwarsreport.report.entity.Report;
import pl.wuniszewski.starwarsreport.report.entity.Result;
import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;
import pl.wuniszewski.starwarsreport.report.exception.NotExistingResourceException;
import pl.wuniszewski.starwarsreport.report.repository.ReportRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReportGeneratorService {
    private ReportRepository reportRepository;
    private IntegrationService integrationService;
    private final Pattern idExtractionPattern = Pattern.compile("(\\d+)(?!.*\\d)");


    @Autowired
    public ReportGeneratorService(ReportRepository reportRepository, IntegrationService integrationService) {
        this.reportRepository = reportRepository;
        this.integrationService = integrationService;
    }

    public void createReport(Long id, QueryCriteriaDto criteria) {
        Report newReport = new Report(id);
        setReportCriteria(newReport, criteria);
        newReport.setResult(generateResults(criteria));
        saveReport(newReport);
    }

    public void updateReport(Report oldReport, QueryCriteriaDto criteria) {
        setReportCriteria(oldReport, criteria);
        oldReport.setResult(generateResults(criteria));
        saveReport(oldReport);
    }

    private void saveReport(Report report) {
        reportRepository.save(report);
    }

    private Set<Result> generateResults(QueryCriteriaDto criteria) {
        Set<Result> results = new HashSet<>();
        List<PlanetDto> planetSearchResults = integrationService.getPlanetsByName(criteria.getQueryCriteriaPlanetName());
        if (isPlanetCorrect(criteria, planetSearchResults)) {
            PlanetDto planet = planetSearchResults.stream()
                    .findFirst()
                    .get();
            for (String residentUrl : planet.getResidents()) {
                CharacterDto resident = integrationService.getPlanetResidents(residentUrl);
                if (nameContainsPhrase(criteria, resident)) {
                    for (String filmUrl : resident.getFilms()) {
                        FilmDto film = integrationService.getFilmsByEndpoint(filmUrl);
                        try {
                            results.add(createResult(film, resident, planet));
                        } catch (IncorrectUrlException e) {
                            throw new NotExistingResourceException("Wrong URL in resource");
                        }

                    }
                }
            }
        }
        return results;
    }

    private Result createResult(FilmDto film, CharacterDto resident, PlanetDto planet) throws IncorrectUrlException {
        Result newResult = new Result();
        newResult.setFilmId(extractIdFromUrl(film.getUrl()));
        newResult.setFilmName(film.getTitle());
        newResult.setCharacterId(extractIdFromUrl(resident.getUrl()));
        newResult.setCharacterName(resident.getName());
        newResult.setPlanetId(extractIdFromUrl(planet.getUrl()));
        newResult.setPlanetName(planet.getName());
        return newResult;
    }

    private Long extractIdFromUrl(String url) throws IncorrectUrlException {
        Matcher matcher = idExtractionPattern.matcher(url);
        String id = "";
        if (matcher.find()) {
            id = matcher.group();
        }
        if ("".equals(id)) {
            throw new IncorrectUrlException("Wrong url pattern");
        }
        return Long.valueOf(id);
    }

    private boolean isPlanetCorrect(QueryCriteriaDto criteria, List<PlanetDto> planetResults) {
        return !planetResults.isEmpty() | isAnyPlanetSuitable(criteria, planetResults);
    }

    private boolean isAnyPlanetSuitable(QueryCriteriaDto criteria, List<PlanetDto> planetResults) {
        return !planetResults.stream()
                .filter(planet -> planet.getName().toLowerCase().
                        equals(criteria.getQueryCriteriaPlanetName()))
                .collect(Collectors.toList())
                .isEmpty();
    }

    private boolean nameContainsPhrase(QueryCriteriaDto criteria, CharacterDto resident) {
        return resident.getName().toLowerCase().contains(criteria.getQueryCriteriaCharacterPhrase().toLowerCase());
    }

    private void setReportCriteria(Report report, QueryCriteriaDto queryCriteria) {
        report.setQueryCriteriaPlanetName(queryCriteria.getQueryCriteriaPlanetName());
        report.setQueryCriteriaCharacterPhrase(queryCriteria.getQueryCriteriaCharacterPhrase());
    }

    public void deleteById(Long id) throws NotExistingResourceException {
        if (!reportRepository.existsById(id)) {
            throw new NotExistingResourceException("No resource found with ID: " + id);
        }
        reportRepository.deleteById(id);
    }

    public void deleteAll() {
        reportRepository.deleteAll();
    }


}
