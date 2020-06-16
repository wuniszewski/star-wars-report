package pl.wuniszewski.starwarsreport.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class ReportDto {

    private Long id;
    @JsonProperty("query_criteria_character_phrase")
    private String queryCriteriaCharacterPhrase;
    @JsonProperty("query_criteria_planet_name")
    private String queryCriteriaPlanetName;
    private Set<ResultDto> result = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueryCriteriaCharacterPhrase() {
        return queryCriteriaCharacterPhrase;
    }

    public void setQueryCriteriaCharacterPhrase(String queryCriteriaCharacterPhrase) {
        this.queryCriteriaCharacterPhrase = queryCriteriaCharacterPhrase;
    }

    public String getQueryCriteriaPlanetName() {
        return queryCriteriaPlanetName;
    }

    public void setQueryCriteriaPlanetName(String queryCriteriaPlanetName) {
        this.queryCriteriaPlanetName = queryCriteriaPlanetName;
    }

    public Set<ResultDto> getResult() {
        return result;
    }

    public void setResult(Set<ResultDto> result) {
        this.result = result;
    }
}
