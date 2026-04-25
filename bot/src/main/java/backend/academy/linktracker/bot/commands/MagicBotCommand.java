package backend.academy.linktracker.bot.commands;

import backend.academy.linktracker.bot.dto.response.ApiErrorResponse;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MagicBotCommand {
    Logger log = LoggerFactory.getLogger(MagicBotCommand.class);
    public SendMessage process(Update update);

    public Command getCommand();
    default void handleError(ApiErrorResponse error, String funkName){
        log.atError()
            .addKeyValue("Code", error.getCode())
            .addKeyValue("Description", error.getDescription())
            .addKeyValue("Exception name", error.getExceptionName())
            .addKeyValue("Message", error.getExceptionMessage())
            .addKeyValue("Stack trace", error.getStacktrace())
            .log(funkName);
//        System.out.println(error.getDescription());
//        System.out.println(error.getExceptionName());
//        System.out.println(error.getExceptionMessage());
//        System.out.println(error.getStacktrace());
//        System.out.println();
    }
}
