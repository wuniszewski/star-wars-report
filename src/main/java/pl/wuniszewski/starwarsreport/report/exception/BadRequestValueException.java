package pl.wuniszewski.starwarsreport.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request value")
public class BadRequestValueException extends RuntimeException {
    public BadRequestValueException(String message) {
        super(message);
    }
}
