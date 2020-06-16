package pl.wuniszewski.starwarsreport.integration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetSearchListOutcomeDto;
import pl.wuniszewski.starwarsreport.integration.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IntegrationServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private IntegrationService service;

    private List<PlanetDto> planets = new ArrayList<>();
    private PlanetSearchListOutcomeDto planetSearch = new PlanetSearchListOutcomeDto();
    private CharacterDto characterDto = new CharacterDto();
    private FilmDto filmDto = new FilmDto();

    @BeforeEach
    public void setUp () {
        filmDto.setTitle("Rise of the Empire");
        filmDto.setUrl("http://localhost:8080/api/films/2/");
        characterDto.setName("Luke");
        characterDto.setUrl("http://localhost:8080/api/people/2/");
        characterDto.setFilms(Arrays.asList("http://localhost:8080/api/films/2/"));
        PlanetDto planetDto = new PlanetDto();
        planetDto.setResidents(Arrays.asList("http://localhost:8080/api/people/2/"));
        planetDto.setName("Tatooine");
        planetDto.setUrl("http://localhost:8080/api/planets/2/");
        planets.add(planetDto);
        planetSearch.setResults(planets);
    }
    @Test
    public void getPlanetsByName_shouldReturnListWithPlanetsGivenValidPlanetName () throws ResourceNotFoundException {
        //given
        //null added because of the @Value annotation not working
        when(restTemplate.getForObject("nullTatooine", PlanetSearchListOutcomeDto.class)).thenReturn(planetSearch);
        //when
        List<PlanetDto> result = service.getPlanetsByName("Tatooine");
        //then
        assertEquals(result, planets);
    }
    @Test
    public void getPlanetsByName_shouldThrowResourceNotFoundExcGivenWrongPlanetName () {
        //given
        when(restTemplate.getForObject("null", PlanetSearchListOutcomeDto.class))
                .thenReturn(null);
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getPlanetsByName("");
        });
    }
    @Test
    public void getCharacterByEndpoint_shouldReturnCharacterGivenRightUrl () throws ResourceNotFoundException {
        //given
        when(restTemplate.getForObject(anyString(), any())).thenReturn(characterDto);
        //when
        CharacterDto character = service.getCharacterByEndpoint("http://localhost:8080/api/people/2/");
        //then
        assertEquals(character, characterDto);
    }
    @Test
    public void getCharacterByEndpoint_shouldThrowResourceNotFoundGivenWrongUrl () {
        //given
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getCharacterByEndpoint("wrong url");
        });
    }
    @Test
    public void getCharacterByEndpoint_shouldThrowResourceNotFoundGivenRestTemplateException() {
        //given
        when(restTemplate.getForObject(anyString(), any())).thenThrow(RestClientException.class);
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getCharacterByEndpoint("wrong url");
        });
    }
    @Test
    public void getFilmByEndpoint_shouldReturnFilmGivenRightUrl () throws ResourceNotFoundException {
        //given
        when(restTemplate.getForObject(anyString(), any())).thenReturn(filmDto);
        //when
        FilmDto film = service.getFilmByEndpoint("http://localhost:8080/api/films/2/");
        //then
        assertEquals(film, filmDto);
    }
    @Test
    public void getFilmByEndpoint_shouldThrowResourceNotFoundGivenWrongUrl () {
        //given
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getFilmByEndpoint("wrong url");
        });
    }
    @Test
    public void getFilmByEndpoint_shouldThrowResourceNotFoundGivenRestTemplateException () {
        //given
        when(restTemplate.getForObject(anyString(), any())).thenThrow(RestClientException.class);
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getFilmByEndpoint("wrong url");
        });
    }



}