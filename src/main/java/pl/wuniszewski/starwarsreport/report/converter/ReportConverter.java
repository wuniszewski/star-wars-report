package pl.wuniszewski.starwarsreport.report.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wuniszewski.starwarsreport.report.dto.ReportDto;
import pl.wuniszewski.starwarsreport.report.entity.Report;

@Component
public class ReportConverter {
    private ModelMapper modelMapper;

    @Autowired
    public ReportConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReportDto convertToDto (Report report) {
        return modelMapper.map(report, ReportDto.class);
    }
}
