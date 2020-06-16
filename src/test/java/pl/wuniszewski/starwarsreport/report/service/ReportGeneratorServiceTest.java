package pl.wuniszewski.starwarsreport.report.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.integration.exception.ResourceNotFoundException;
import pl.wuniszewski.starwarsreport.integration.service.IntegrationService;
import pl.wuniszewski.starwarsreport.report.converter.ResultConverter;
import pl.wuniszewski.starwarsreport.report.dto.QueryCriteriaDto;
import pl.wuniszewski.starwarsreport.report.entity.Report;
import pl.wuniszewski.starwarsreport.report.entity.Result;
import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;
import pl.wuniszewski.starwarsreport.report.exception.NotExistingResourceException;
import pl.wuniszewski.starwarsreport.report.repository.ReportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportGeneratorServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private IntegrationService integrationService;
    @Mock
    private ResultConverter resultConverter;
    @InjectMocks
    private ReportGeneratorService service;

    List<Report> reportList = new ArrayList<>();

    @BeforeEach
    private void setUp() {
        Report report1 = new Report(1L);
        Result result1 = new Result.ResultBuilder()
                .setCharacterId(1L).setCharacterName("character1")
                .build();
        report1.getResult().add(result1);
        report1.setQueryCriteriaPlanetName("criteria planet 1");
        report1.setQueryCriteriaCharacterPhrase("criteria phrase 1");
        report1.getResult().add(result1);
        Report report2 = new Report(2L);
        Result result2 = new Result.ResultBuilder()
                .setCharacterId(2L).setCharacterName("character2")
                .build();
        report2.getResult().add(result2);
        report2.setQueryCriteriaPlanetName("criteria planet 2");
        report2.setQueryCriteriaCharacterPhrase("criteria phrase 2");
        report2.getResult().add(result2);
        this.reportList.add(report1);
        this.reportList.add(report2);
    }

    @Test
    public void deleteAll_shouldEmptyReportList() {
        //given
        doAnswer(invocation -> {
            reportList.clear();
            return null;
        }).when(reportRepository).deleteAll();
        //when
        service.deleteAll();
        //then
        assertTrue(reportList.isEmpty());
    }

    @Test
    public void deleteById_shouldDeleteWhenIdExists() {
        //given
        when(reportRepository.existsById(2L)).thenReturn(true);
        doAnswer(invocationOnMock -> {
            reportList.remove(1);
            return null;
        }).when(reportRepository).deleteById(anyLong());
        //when
        service.deleteById(2L);
        //then
        assertTrue(reportList.size() == 1);
    }

    @Test
    public void deleteById_shouldThrowNoExistingResourceExcGivenMissingId() {
        //given
        when(reportRepository.existsById(2L)).thenReturn(false);
        //then
        assertThrows(NotExistingResourceException.class, () -> {
            service.deleteById(2L);
        });
    }

    @Test
    public void saveReport_shouldAddToListGivenReport() {
        //given
        doAnswer(i -> {
            reportList.add(new Report(3L));
            return null;
        }).when(reportRepository).save(any(Report.class));
        //when
        service.saveReport(new Report(3L));
        //then
        assertTrue(reportList.get(2).getId().equals(3L));
    }

    @Test
    public void createReport_shouldAddNewReportToListGivenAnyParameters() throws ResourceNotFoundException {
        //given
        QueryCriteriaDto criteria = new QueryCriteriaDto();
        criteria.setQueryCriteriaPlanetName("planet");
        criteria.setQueryCriteriaCharacterPhrase("phrase");
        when(integrationService.getPlanetsByName(anyString())).thenReturn(new ArrayList<PlanetDto>());
        doAnswer(i -> {
            reportList.clear();
            reportList.add(new Report(2L));
            return null;
        }).when(reportRepository).save(any(Report.class));
        //when
        service.createReport(2L, criteria);
        //then
        assertTrue(reportList.size() == 1);
    }

    @Test
    public void createReport_shouldGenerateAndSaveReportGivenCriteria()
            throws ResourceNotFoundException, IncorrectUrlException {
        //given
        Result result = new Result.ResultBuilder().setCharacterName("phrase").setFilmName("film").setPlanetName("planet").build();
        QueryCriteriaDto criteria = new QueryCriteriaDto();
        Report report = new Report(2L);
        report.getResult().add(result);
        report.setQueryCriteriaPlanetName("planet");
        report.setQueryCriteriaCharacterPhrase("phrase");
        criteria.setQueryCriteriaPlanetName("planet");
        criteria.setQueryCriteriaCharacterPhrase("phrase");
        PlanetDto planet = new PlanetDto();
        planet.setName("planet");
        planet.setResidents(Arrays.asList("resident"));
        List<PlanetDto> planets = Arrays.asList(planet);
        CharacterDto character = new CharacterDto();
        character.setName("phrase");
        character.setFilms(Arrays.asList("films"));
        FilmDto film = new FilmDto();
        film.setTitle("film");
        when(integrationService.getPlanetsByName(anyString())).thenReturn(planets);
        when(integrationService.getCharacterByEndpoint(anyString())).thenReturn(character);
        when(integrationService.getFilmsByEndpoint(anyString())).thenReturn(film);
        when(resultConverter.convertToEntity(any(FilmDto.class), any(CharacterDto.class), any(PlanetDto.class)))
                .thenReturn(result);
        doAnswer(i -> {
            reportList.clear();
            reportList.add(new Report(2L));
            return null;
        }).when(reportRepository).save(any(Report.class));
        //when
        service.createReport(2L, criteria);
        //then
        verify(resultConverter, times(1)).convertToEntity(any(FilmDto.class), any(CharacterDto.class), any(PlanetDto.class));
    }

    @Test
    public void createReport_shouldThrowNotExistingResourceExcGivenResultConversionFail()
            throws ResourceNotFoundException, IncorrectUrlException {
        //given
        Result result = new Result.ResultBuilder().setCharacterName("phrase").setFilmName("film").setPlanetName("planet").build();
        QueryCriteriaDto criteria = new QueryCriteriaDto();
        Report report = new Report(2L);
        report.getResult().add(result);
        report.setQueryCriteriaPlanetName("planet");
        report.setQueryCriteriaCharacterPhrase("phrase");
        criteria.setQueryCriteriaPlanetName("planet");
        criteria.setQueryCriteriaCharacterPhrase("phrase");
        PlanetDto planet = new PlanetDto();
        planet.setName("planet");
        planet.setResidents(Arrays.asList("resident"));
        List<PlanetDto> planets = Arrays.asList(planet);
        CharacterDto character = new CharacterDto();
        character.setName("phrase");
        character.setFilms(Arrays.asList("films"));
        FilmDto film = new FilmDto();
        film.setTitle("film");
        when(integrationService.getPlanetsByName(anyString())).thenReturn(planets);
        when(integrationService.getCharacterByEndpoint(anyString())).thenReturn(character);
        when(integrationService.getFilmsByEndpoint(anyString())).thenReturn(film);
        when(resultConverter.convertToEntity(any(FilmDto.class), any(CharacterDto.class), any(PlanetDto.class)))
                .thenThrow(IncorrectUrlException.class);
        //then
        assertThrows(NotExistingResourceException.class, () -> {
            service.createReport(2L, criteria);
        });
    }

    @Test
    public void createReport_shouldCreateReportWithoutResultsGivenEmptyPlanetList()
            throws ResourceNotFoundException, IncorrectUrlException {
        //given
        QueryCriteriaDto criteria = new QueryCriteriaDto();
        criteria.setQueryCriteriaPlanetName("planet");
        criteria.setQueryCriteriaCharacterPhrase("phrase");
        List<PlanetDto> planets = new ArrayList<>();
        when(integrationService.getPlanetsByName(anyString())).thenReturn(planets);
        //when
        service.createReport(2L, criteria);
        //then
        verify(integrationService, times(0)).getCharacterByEndpoint(anyString());
    }
    @Test
    public void updateReport_shouldChangeIDInListGivenDifferentReport () throws ResourceNotFoundException {
        //given
        Report oldReport = new Report(10L);
        QueryCriteriaDto criteria = new QueryCriteriaDto();
        criteria.setQueryCriteriaPlanetName("planet");
        criteria.setQueryCriteriaCharacterPhrase("phrase");
        when(integrationService.getPlanetsByName(anyString())).thenReturn(new ArrayList<PlanetDto>());
        doAnswer(i -> {
            reportList.get(1).setId(oldReport.getId());
            return null;
        }).when(reportRepository).save(any(Report.class));
        //when
        service.updateReport(oldReport, criteria);
        //then
        assertTrue(reportList.get(1).getId() == 10L);
    }
}