package backend.academy.linktracker.bot.commands;

import backend.academy.linktracker.bot.client.ScrapperApiException;
import backend.academy.linktracker.bot.client.ScrapperClient;
import backend.academy.linktracker.bot.state.State;
import backend.academy.linktracker.bot.state.UserSession;
import backend.academy.linktracker.bot.state.UserStateService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class TrackCommand implements MagicBotCommand{
    @Autowired
    UserStateService userStateService;
    @Autowired
    ScrapperClient scrapperClient;

    @Override
    public SendMessage process(Update update){
        long chatId = update.message().chat().id();
        UserSession session = userStateService.getSession(chatId);
        switch (session.getState()){
            case NO:
                session.setState(State.TYPING_LINK);
                return new SendMessage(chatId, "Введите ссылку для отслеживания");
            case TYPING_LINK:
                String link = update.message().text().strip();
                session.setLink(link);
                session.setState(State.TYPING_TAGS);
                log.info("State updated: {}", userStateService.getSession(chatId).getState());
                return new SendMessage(chatId, "Введите теги через запятую и пробел. Если теги не нужны, введите '''-'''");
            case TYPING_TAGS:
                String text = update.message().text().strip();
                List<String> tags;
                if (text.equals("-")){
                    tags = List.of();
                } else {
                    tags = List.of(text.split(", "));
                }
                try{
                    scrapperClient.addLink(
                        chatId,
                        session.getLink(),
                        tags
                    );
                    userStateService.resetSession(chatId);
                    log.info("Session reset");
                    return new SendMessage(chatId, "Ссылка добавлена для отслеживания.");
                } catch (ScrapperApiException e){
                    handleError(e.getError(), e.getFunkName());
                    return new SendMessage(chatId, Objects.requireNonNullElse(e.getError().getDescription(), "Что-то пошло не так."));
                }
        }
        return new SendMessage(chatId, "Что-то пошло не так. Попробуйте снова.");
    }
    @Override
    public Command getCommand(){
        return Command.TRACK;
    }
}
