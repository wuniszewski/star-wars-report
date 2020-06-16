package pl.wuniszewski.starwarsreport.report.exception;

public class BadRequestValueException extends RuntimeException {

    public BadRequestValueException(String message) {
        super(message);
    }
}
