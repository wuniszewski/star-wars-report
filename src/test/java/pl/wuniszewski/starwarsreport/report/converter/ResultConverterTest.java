package pl.wuniszewski.starwarsreport.report.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.report.entity.Result;
import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;
import pl.wuniszewski.starwarsreport.report.util.IdFromUrlExtraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ResultConverterTest {


    @InjectMocks
    private ResultConverter resultConverter;

    @Test
    public void convertToEntity_shouldResultFieldsNamesBeEqualToGivenParamFieldsAfterExtraction() throws IncorrectUrlException {
        //given
        FilmDto filmDto = new FilmDto("Film", "http://localhost:8080/api/films/3/");
        CharacterDto characterDto = new CharacterDto("Resident", "http://localhost:8080/api/people/3/");
        PlanetDto planetDto = new PlanetDto("Planet", "http://localhost:8080/api/planet/3/");
        //when
        Result result = resultConverter.convertToEntity(filmDto, characterDto, planetDto);
        //then
        assertEquals(result.getCharacterId(), IdFromUrlExtraction.extractIdFromUrl(characterDto.getUrl()));
        assertEquals(result.getFilmId(), IdFromUrlExtraction.extractIdFromUrl(filmDto.getUrl()));
        assertEquals(result.getPlanetId(), IdFromUrlExtraction.extractIdFromUrl(planetDto.getUrl()));
        assertEquals(result.getCharacterName(), characterDto.getName());
        assertEquals(result.getFilmName(), filmDto.getTitle());
        assertEquals(result.getPlanetName(), planetDto.getName());
    }

    @Test
    public void convertToEntity_shouldThrowIncorrectUrlExcGivenWrongUrlParams() {
        //given
        FilmDto filmDto = new FilmDto("Film", "http://localhost:8080/api/films/");
        CharacterDto characterDto = new CharacterDto("Resident", "wrong url");
        PlanetDto planetDto = new PlanetDto("Good planet", "http://localhost:8080/api/planet/3/");
        //then
        assertThrows(IncorrectUrlException.class, () -> {
            resultConverter.convertToEntity(filmDto, characterDto, planetDto);
        });
    }

    @Test
    public void convertToEntity_shouldThrowNullPointerGivenEmptyParams() {
        //given
        FilmDto filmDto = new FilmDto();
        CharacterDto characterDto = new CharacterDto();
        PlanetDto planetDto = new PlanetDto();
        //then
        assertThrows(NullPointerException.class, () -> {
            resultConverter.convertToEntity(filmDto, characterDto, planetDto);
        });
    }

}