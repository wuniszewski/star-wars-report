package pl.wuniszewski.starwarsreport.integration.dto;

public class FilmDto {

    private String title;
    private String url;

    public FilmDto(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public FilmDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
