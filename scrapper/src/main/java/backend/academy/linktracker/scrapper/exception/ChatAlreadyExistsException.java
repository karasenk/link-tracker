package backend.academy.linktracker.scrapper.exception;

public class ChatAlreadyExistsException extends RuntimeException {
    public ChatAlreadyExistsException(Long chatId) {
        super("Chat with id " + chatId + " already exists");
    }
}
