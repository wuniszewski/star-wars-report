package pl.wuniszewski.starwarsreport.report.entity;

import pl.wuniszewski.starwarsreport.integration.dto.CharacterDto;
import pl.wuniszewski.starwarsreport.integration.dto.FilmDto;
import pl.wuniszewski.starwarsreport.integration.dto.PlanetDto;
import pl.wuniszewski.starwarsreport.report.converter.UrlToIdConverter;
import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "results")
public class Result implements UrlToIdConverter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long filmId;
    private String filmName;
    private Long characterId;
    private String characterName;
    private Long planetId;
    private String planetName;

    public Result() {
    }

    public Result(Long filmId, String filmName, Long characterId, String characterName, Long planetId, String planetName) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.characterId = characterId;
        this.characterName = characterName;
        this.planetId = planetId;
        this.planetName = planetName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public static class ResultBuilder {
        private Long filmId;
        private String filmName;
        private Long characterId;
        private String characterName;
        private Long planetId;
        private String planetName;

        public ResultBuilder() {
        }

        public ResultBuilder setFilmInfo(FilmDto film) throws IncorrectUrlException {
            this.filmId = UrlToIdConverter.extractIdFromUrl(film.getUrl());
            this.filmName = film.getTitle();
            return this;
        }

        public ResultBuilder setResidentInfo(CharacterDto character) throws IncorrectUrlException {
            this.characterId = UrlToIdConverter.extractIdFromUrl(character.getUrl());
            this.filmName = character.getName();
            return this;
        }

        public ResultBuilder setPlanetInfo(PlanetDto planet) throws IncorrectUrlException {
            this.planetId = UrlToIdConverter.extractIdFromUrl(planet.getUrl());
            this.planetName = planet.getName();
            return this;
        }

        public Result build() {
            return new Result(filmId, filmName, characterId, characterName,
                    planetId, planetName);
        }
    }
}
