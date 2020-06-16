package pl.wuniszewski.starwarsreport.integration.dto;

import java.util.List;

public class PlanetSearchListOutcomeDto {

    private List<PlanetDto> results;

    public List<PlanetDto> getResults() {
        return results;
    }

    public void setResults(List<PlanetDto> results) {
        this.results = results;
    }
}
