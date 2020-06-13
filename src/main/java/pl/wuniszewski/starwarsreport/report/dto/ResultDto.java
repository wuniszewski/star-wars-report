package pl.wuniszewski.starwarsreport.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultDto {
    @JsonProperty("film_id")
    private Long filmId;
    @JsonProperty("film_name")
    private String filmName;
    @JsonProperty("character_id")
    private Long characterId;
    @JsonProperty("character_name")
    private String characterName;
    @JsonProperty("planet_id")
    private Long planetId;
    @JsonProperty("planet_name")
    private String planetName;

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
}
