package pl.wuniszewski.starwarsreport.report.exception;

public class NotExistingResourceException extends RuntimeException {

    public NotExistingResourceException(String message) {
        super(message);
    }
}
