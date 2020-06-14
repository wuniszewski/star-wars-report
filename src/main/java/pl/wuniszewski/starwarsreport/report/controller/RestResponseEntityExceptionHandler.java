package pl.wuniszewski.starwarsreport.report.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.wuniszewski.starwarsreport.report.exception.BadRequestValueException;
import pl.wuniszewski.starwarsreport.report.exception.NotExistingResourceException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadRequestValueException.class)
    protected ResponseEntity<Object> handleBadRequest (
            RuntimeException exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = NotExistingResourceException.class)
    protected ResponseEntity<Object> handleNotFoundException (
            RuntimeException exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
