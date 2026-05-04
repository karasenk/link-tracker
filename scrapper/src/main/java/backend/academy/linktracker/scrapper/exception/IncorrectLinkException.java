package backend.academy.linktracker.scrapper.exception;

import java.util.List;

public class IncorrectLinkException extends RuntimeException {
    public IncorrectLinkException(String link, List<String> patterns) {
        super("Link " + link + " doesn't match any of this patterns:\n" + String.join("\n", patterns));
    }
}
