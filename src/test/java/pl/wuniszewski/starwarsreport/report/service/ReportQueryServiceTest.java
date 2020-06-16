package pl.wuniszewski.starwarsreport.report.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wuniszewski.starwarsreport.report.entity.Report;
import pl.wuniszewski.starwarsreport.report.exception.NotExistingResourceException;
import pl.wuniszewski.starwarsreport.report.repository.ReportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportQueryServiceTest {

    @Mock
    private ReportRepository repository;
    @InjectMocks
    private ReportQueryService service;

    @Test
    public void findById_shouldReturnReportWhenReportExists() {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(new Report()));
        assertTrue(service.findById(2L) != null);
    }

    @Test
    public void findById_shouldThrowNotExistingResourceExcGivenMissingId() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotExistingResourceException.class, () -> {
            service.findById(2L);
        });
    }

    @Test
    public void findAll_shouldReturnListWhenInDBExistReports() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Report(), new Report()));
        assertFalse(service.findAll().isEmpty());
    }

    @Test
    public void findAll_shouldReturnEmptyListWhenNoReportsInDB() {
        when(repository.findAll()).thenReturn(new ArrayList<Report>());
        assertTrue(service.findAll().isEmpty());
    }
}