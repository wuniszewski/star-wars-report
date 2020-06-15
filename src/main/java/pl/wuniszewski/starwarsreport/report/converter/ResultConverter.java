package pl.wuniszewski.starwarsreport.report.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.report.entity.Result;
import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;
import pl.wuniszewski.starwarsreport.report.util.IdFromUrlExtraction;

@Component
public class ResultConverter {

    public Result convertToEntity(FilmDto film, CharacterDto character, PlanetDto planet) throws IncorrectUrlException {
        return new Result.ResultBuilder()
                .setFilmId(IdFromUrlExtraction.extractIdFromUrl(film.getUrl()))
                .setFilmName(film.getTitle())
                .setCharacterId(IdFromUrlExtraction.extractIdFromUrl(character.getUrl()))
                .setCharacterName(character.getName())
                .setPlanetId(IdFromUrlExtraction.extractIdFromUrl(planet.getUrl()))
                .setPlanetName(planet.getName())
                .build();
    }
}
