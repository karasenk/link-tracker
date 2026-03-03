package backend.academy.linktracker.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("/help")
public class HelpCommand extends MagicBotCommand {
    @Autowired
    private ApplicationContext applicationContext;

    public static final String DESCRIPTION = "Вывод списка доступных команд";

    @Override
    public String process() {
        StringBuilder messageText = new StringBuilder();
        for (String commandName : applicationContext.getBeanNamesForType(MagicBotCommand.class)) {
            MagicBotCommand command = applicationContext.getBean(commandName, MagicBotCommand.class);
            messageText
                    .append(commandName)
                    .append(" - ")
                    .append(command.description())
                    .append("\n");
        }
        return messageText.toString();
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
