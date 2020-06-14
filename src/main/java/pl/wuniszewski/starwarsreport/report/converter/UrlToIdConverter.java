package pl.wuniszewski.starwarsreport.report.converter;

import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface UrlToIdConverter {

    static Long extractIdFromUrl (String url) throws IncorrectUrlException {
        Pattern idExtractionPattern = Pattern.compile("(\\d+)(?!.*\\d)");
        Matcher matcher = idExtractionPattern.matcher(url);
        String id = "";
        if (matcher.find()) {
            id = matcher.group();
        }
        if ("".equals(id)) {
            throw new IncorrectUrlException("Wrong url pattern");
        }
        return Long.valueOf(id);
    }
}
