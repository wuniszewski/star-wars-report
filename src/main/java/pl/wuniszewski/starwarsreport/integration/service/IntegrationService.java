package pl.wuniszewski.starwarsreport.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetSearchListOutcomeDto;
import pl.wuniszewski.starwarsreport.integration.exception.ResourceNotFoundException;

import java.util.List;

@Component
public class IntegrationService {

    private RestTemplate restTemplate;
    @Value("${planetSearchUrlPrefix}")
    private String urlPlanetSearchPrefix;


    @Autowired
    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PlanetDto> getPlanetsByName(String planetName) throws ResourceNotFoundException {
        return getPlanetSearchListOutcome(planetName).getResults();
    }

    private PlanetSearchListOutcomeDto getPlanetSearchListOutcome(String planetName) throws ResourceNotFoundException {
        PlanetSearchListOutcomeDto planetSearch;
        try {
            planetSearch = restTemplate.getForObject(urlPlanetSearchPrefix + planetName,
                    PlanetSearchListOutcomeDto.class);
        } catch (RestClientException e) {
            planetSearch = new PlanetSearchListOutcomeDto();
        }
        if (planetSearch == null || planetSearch.getResults() == null) {
            throw new ResourceNotFoundException();
        }
        return planetSearch;
    }

    public CharacterDto getCharacterByEndpoint(String url) throws ResourceNotFoundException {
        CharacterDto character;
        try {
            character = restTemplate.getForObject(url, CharacterDto.class);
        } catch (RestClientException e) {
            character = new CharacterDto();
        }
        if (isCharacterNullOrEmpty(character)) {
            throw new ResourceNotFoundException();
        }
        return character;
    }

    private boolean isCharacterNullOrEmpty(CharacterDto character) {
        return character == null || character.getFilms() == null || character.getName() == null | character.getUrl() == null;
    }

    public FilmDto getFilmByEndpoint(String url) throws ResourceNotFoundException {
        FilmDto film;
        try {
            film = restTemplate.getForObject(url, FilmDto.class);
        } catch (RestClientException e) {
            film = new FilmDto();
        }
        if (isFilmNullOrEmpty(film)) {
            throw new ResourceNotFoundException();
        }
        return film;
    }

    private boolean isFilmNullOrEmpty(FilmDto film) {
        return film == null || film.getTitle() == null || film.getUrl() == null;
    }


}
