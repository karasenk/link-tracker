package backend.academy.linktracker.scrapper.exception;

public class LinkAlreadyExistsException extends RuntimeException {
    public LinkAlreadyExistsException(String link) {
        super("Link " + link + " is already being tracked");
    }
}
