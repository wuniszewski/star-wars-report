package pl.wuniszewski.starwarsreport.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetSearchListOutcomeDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetSearchResultDto;
import pl.wuniszewski.starwarsreport.integration.exception.ResourceNotFoundException;

import java.util.ArrayList;
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

    public PlanetSearchListOutcomeDto getPlanetByName(String planetName) {
        return restTemplate.getForObject(urlPlanetSearchPrefix + planetName,
                PlanetSearchListOutcomeDto.class);
    }

    public PlanetSearchResultDto getPlanetSearchResult(PlanetSearchListOutcomeDto planetSearchListOutcomeDto) {
        return planetSearchListOutcomeDto.getResults().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No planet found"));
    }

    public List<CharacterDto> getPlanetResidents(PlanetSearchResultDto planet) {
        List<CharacterDto> residents = new ArrayList<>();
        planet.getResidents()
                .forEach(resident ->
                        residents.add(restTemplate.getForObject(resident, CharacterDto.class)));
        return residents;
    }

    public List<FilmDto> getFilmsByCharacter(CharacterDto characterDto) {
        List<FilmDto> films = new ArrayList<>();
        characterDto.getFilms().forEach(film ->
                films.add(restTemplate.getForObject(film, FilmDto.class)));
        return films;
    }


}
