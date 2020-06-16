package pl.wuniszewski.starwarsreport.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WrongUrlsInResourceException extends RuntimeException {
    public WrongUrlsInResourceException(String message) {
        super(message);
    }
}
