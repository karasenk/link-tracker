package backend.academy.linktracker.bot.client;

import backend.academy.linktracker.bot.dto.response.ApiErrorResponse;
import lombok.Getter;

@Getter
public class ScrapperApiException extends RuntimeException {
    private final ApiErrorResponse error;
    private final String funkName;

    public ScrapperApiException(ApiErrorResponse error, String funkName) {
        super(error != null ? error.getExceptionMessage() : "Unknown error");
        this.error = error;
        this.funkName = funkName;
    }
}
