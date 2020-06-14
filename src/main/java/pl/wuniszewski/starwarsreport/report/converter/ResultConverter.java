package pl.wuniszewski.starwarsreport.report.converter;

import org.springframework.stereotype.Component;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.report.entity.Result;
import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;

@Component
public class ResultConverter {
    public Result convertToEntity (FilmDto film, CharacterDto character, PlanetDto planet) throws IncorrectUrlException {
        return new Result.ResultBuilder()
                .setFilmInfo(film)
                .setResidentInfo(character)
                .setPlanetInfo(planet)
                .build();
    }
}
