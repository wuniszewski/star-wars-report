package pl.wuniszewski.starwarsreport.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetSearchListOutcomeDto;

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

    public List<PlanetDto> getPlanetsByName(String planetName) {
        return getPlanetSearchListOutcome(planetName).getResults();
    }
    private PlanetSearchListOutcomeDto getPlanetSearchListOutcome (String planetName) {
        return restTemplate.getForObject(urlPlanetSearchPrefix + planetName,
                PlanetSearchListOutcomeDto.class);
    }

    public CharacterDto getPlanetResidents(String url) {
        return restTemplate.getForObject(url, CharacterDto.class);
    }

    public FilmDto getFilmsByEndpoint(String url) {
        return restTemplate.getForObject(url, FilmDto.class);
    }


}
