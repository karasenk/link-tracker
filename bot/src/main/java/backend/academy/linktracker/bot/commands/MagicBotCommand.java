package backend.academy.linktracker.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface MagicBotCommand {
    public SendMessage process(Update update);

    public Command getCommand();
}
