package pl.wuniszewski.starwarsreport.integration.dto;

import java.util.List;

public class PlanetDto {
    private String name;
    private String url;
    private List<String> residents;

    public PlanetDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public PlanetDto() {
    }

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

    public List<String> getResidents() {
        return residents;
    }

    public void setResidents(List<String> residents) {
        this.residents = residents;
    }
}
