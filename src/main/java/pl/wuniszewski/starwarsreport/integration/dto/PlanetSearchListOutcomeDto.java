package pl.wuniszewski.starwarsreport.integration.dto;

import java.util.List;

public class PlanetSearchListOutcomeDto {
    private List<PlanetSearchResultDto> results;

    public List<PlanetSearchResultDto> getResults() {
        return results;
    }
    public void setResults(List<PlanetSearchResultDto> results) {
        this.results = results;
    }
}
