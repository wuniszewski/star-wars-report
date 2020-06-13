package pl.wuniszewski.starwarsreport.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotExistingResourceException extends Exception {

    public NotExistingResourceException(String message) {
        super(message);
    }
}
