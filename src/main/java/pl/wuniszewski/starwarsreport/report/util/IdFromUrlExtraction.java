package pl.wuniszewski.starwarsreport.report.util;

import pl.wuniszewski.starwarsreport.report.exception.IncorrectUrlException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface IdFromUrlExtraction {

    static Long extractIdFromUrl (String url) {
        Pattern idExtractionPattern = Pattern.compile("(?<![:\\d])[0-9]+");
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
