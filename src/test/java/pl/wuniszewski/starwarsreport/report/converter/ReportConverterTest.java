package pl.wuniszewski.starwarsreport.report.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.wuniszewski.starwarsreport.report.dto.ReportDto;
import pl.wuniszewski.starwarsreport.report.dto.ResultDto;
import pl.wuniszewski.starwarsreport.report.entity.Report;
import pl.wuniszewski.starwarsreport.report.entity.Result;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportConverter reportConverter;

    private Report report;
    private ReportDto reportDto;

    @BeforeEach
    private void setUp() {
        Report report = new Report(1L);
        Result result = new Result.ResultBuilder()
                .setCharacterId(1L).setCharacterName("character")
                .build();
        report.getResult().add(result);
        report.setQueryCriteriaPlanetName("criteria planet");
        report.setQueryCriteriaCharacterPhrase("criteria phrase");
        ReportDto reportDto = new ReportDto();
        ResultDto resultDto = new ResultDto();
        resultDto.setCharacterId(1L);
        resultDto.setCharacterName("character");

        report.getResult().add(result);
        reportDto.setId(1L);
        reportDto.setQueryCriteriaPlanetName("criteria planet");
        reportDto.setQueryCriteriaCharacterPhrase("criteria phrase");
        this.report = report;
        this.reportDto = reportDto;
    }

    @Test
    public void convertToDto_shouldReturnDtoGivenEntity() {
        when(modelMapper.map(report, ReportDto.class)).thenReturn(reportDto);
        assertTrue(reportDto.equals(reportConverter.convertToDto(report)));
    }

    @Test
    public void convertToDto_shouldThrowIllegalArgExceptionGivenNull() {
        when(modelMapper.map(null, ReportDto.class)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            reportConverter.convertToDto(null);
        });
    }

}