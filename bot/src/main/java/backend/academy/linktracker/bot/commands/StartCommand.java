package backend.academy.linktracker.bot.commands;

import org.springframework.stereotype.Component;

@Component("/start")
public class StartCommand extends MagicBotCommand {
    public static final String DESCRIPTION = "Начало работы с Link Tracker";

    @Override
    public String process() {
        return "Добро пожаловать. Используйте команду /help чтобы узнать список команд";
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
