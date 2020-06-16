package pl.wuniszewski.starwarsreport.report.util;

import org.junit.jupiter.api.Test;
import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;

import static org.junit.jupiter.api.Assertions.*;

class IdFromUrlExtractionTest {


    @Test
    public void extractIdFromUrl_shouldReturnLastNumberGivenUrl () throws IncorrectUrlException {
        //given
        String url = "http://localhost:8080/api/people/14/";
        //when
        Long id = IdFromUrlExtraction.extractIdFromUrl(url);
        //then
        assertEquals(id, 14L);
    }
    @Test
    public void extractIdFromUrl_shouldThrowIncorrectUrlExcGivenNotMatchingUrl () {
        //given
        String url = "api/people/first/";
        //then
        assertThrows(IncorrectUrlException.class, () -> {
            IdFromUrlExtraction.extractIdFromUrl(url);
        });
    }
    @Test
    public void extractIdFromUrl_shouldNotCatchPortNumberButThrowExcGivenUrlWithoutId () throws IncorrectUrlException {
        //given
        String url = "http://localhost:8080/api/people/first/";
        //then
        assertThrows(IncorrectUrlException.class, () -> {
            IdFromUrlExtraction.extractIdFromUrl(url);
        });
    }


}