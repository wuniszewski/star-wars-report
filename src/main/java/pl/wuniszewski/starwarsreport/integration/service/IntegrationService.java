package pl.wuniszewski.starwarsreport.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
    private PlanetSearchListOutcomeDto getPlanetSearchListOutcome (String planetName) throws ResourceNotFoundException {
        PlanetSearchListOutcomeDto planetSerch = restTemplate.getForObject(urlPlanetSearchPrefix + planetName,
                PlanetSearchListOutcomeDto.class);
        if (planetSerch == null) {
            throw new ResourceNotFoundException();
        }
        return planetSerch;
    }

    public CharacterDto getCharacterByEndpoint(String url) throws ResourceNotFoundException {
        CharacterDto character = restTemplate.getForObject(url, CharacterDto.class);
        if (character == null) {
            throw new ResourceNotFoundException();
        }
        return character;
    }

    public FilmDto getFilmsByEndpoint(String url) throws ResourceNotFoundException {
        FilmDto film = restTemplate.getForObject(url, FilmDto.class);
        if (film == null) {
            throw new ResourceNotFoundException();
        }
        return film;
    }


}
