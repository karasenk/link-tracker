package backend.academy.linktracker.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements MagicBotCommand {
    static final Command COMMAND = Command.START;

    @Override
    public SendMessage process(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, "Добро пожаловать. Используйте команду /help чтобы узнать список команд");
    }

    @Override
    public Command getCommand() {
        return COMMAND;
    }
}
