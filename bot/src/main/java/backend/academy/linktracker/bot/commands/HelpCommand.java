package backend.academy.linktracker.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements MagicBotCommand {
    public static final Command COMMAND = Command.HELP;

    @Autowired
    List<MagicBotCommand> botCommands;

    @Override
    public SendMessage process(Update update) {
        long chatId = update.message().chat().id();
        StringBuilder messageText = new StringBuilder();
        for (MagicBotCommand botCommand : botCommands) {
            messageText
                    .append(botCommand.getCommand().getName())
                    .append(" - ")
                    .append(botCommand.getCommand().getDescription())
                    .append("\n");
        }
        return new SendMessage(chatId, messageText.toString());
    }

    @Override
    public Command getCommand() {
        return COMMAND;
    }
}
