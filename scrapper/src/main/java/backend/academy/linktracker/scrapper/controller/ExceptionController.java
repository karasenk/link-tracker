package backend.academy.linktracker.scrapper.controller;

import backend.academy.linktracker.scrapper.dto.response.ApiErrorResponse;
import backend.academy.linktracker.scrapper.exception.ChatAlreadyExistsException;
import backend.academy.linktracker.scrapper.exception.ChatNotFoundException;
import backend.academy.linktracker.scrapper.exception.LinkAlreadyExistsException;
import backend.academy.linktracker.scrapper.exception.LinkNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<@NotNull ApiErrorResponse> handleChatNotFound(ChatNotFoundException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
            "Чат не существует",
            "404",
            ChatNotFoundException.class.getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        );
        logAPIError(error);
        return ResponseEntity.status(404).body(error);
    }
    @ExceptionHandler(ChatAlreadyExistsException.class)
    public ResponseEntity<@NotNull ApiErrorResponse> handleChatAlreadyExist(ChatAlreadyExistsException ex){
        ApiErrorResponse error = new ApiErrorResponse(
            "Чат уже существует",
            "409",
            ChatAlreadyExistsException.class.getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        );
        logAPIError(error);
        return ResponseEntity.status(409).body(error);
    }
    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ResponseEntity<@NotNull ApiErrorResponse> handleLinkAlreadyExist(LinkAlreadyExistsException ex){
        ApiErrorResponse error = new ApiErrorResponse(
            "Ссылка уже отслеживается",
            "409",
            LinkAlreadyExistsException.class.getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        );
        logAPIError(error);
        return ResponseEntity.status(409).body(error);
    }
    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<@NotNull ApiErrorResponse> handleLinkNotFoundException(LinkNotFoundException ex){
        ApiErrorResponse error = new ApiErrorResponse(
            "Ссылка не найдена",
            "404",
            LinkNotFoundException.class.getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        );
        logAPIError(error);
        return ResponseEntity.status(404).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NotNull ApiErrorResponse> handleUnexpectedException(Exception ex){
        ApiErrorResponse error = new ApiErrorResponse(
            "Unexpected error",
            "500",
            Exception.class.getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        );
        log.atError().log(ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
    private void logAPIError(ApiErrorResponse error){
        log.atInfo()
            .addKeyValue("name", error.getExceptionName())
            .addKeyValue("code", error.getCode())
            .addKeyValue("message", error.getExceptionMessage())
            .log("API error");
    }
}
