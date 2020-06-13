package pl.wuniszewski.starwarsreport.integration.dto;

import java.util.List;

public class CharacterDto {
    private String name;
    private String url;
    private List<String> films;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getFilms() {
        return films;
    }
    public void setFilms(List<String> films) {
        this.films = films;
    }
}
