package pl.wuniszewski.starwarsreport.report.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "reports")
public class Report {
    @Id
    private Long id;
    private String queryCriteriaCharacterPhrase;
    private String queryCriteriaPlanetName;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_report")
    private Set<Result> result = new HashSet<>();

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

    public Set<Result> getResult() {
        return result;
    }
    public void setResult(Set<Result> result) {
        this.result = result;
    }
}
