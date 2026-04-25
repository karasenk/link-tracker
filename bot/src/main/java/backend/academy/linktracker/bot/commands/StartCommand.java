package backend.academy.linktracker.bot.commands;

import backend.academy.linktracker.bot.client.ScrapperApiException;
import backend.academy.linktracker.bot.client.ScrapperClient;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class StartCommand implements MagicBotCommand {
    @Autowired
    ScrapperClient scrapperClient;

    @Override
    public SendMessage process(Update update) {
        long chatId = update.message().chat().id();
        try {
            scrapperClient.registerChat(chatId);
            return new SendMessage(chatId, "Добро пожаловать. Используйте команду /help чтобы узнать список команд");
        } catch (ScrapperApiException e){
            handleError(e.getError(), e.getFunkName());
            return new SendMessage(chatId, Objects.requireNonNullElse(e.getError().getDescription(), "Что-то пошло не так."));
        }
    }

    @Override
    public Command getCommand() {
        return Command.START;
    }
}
