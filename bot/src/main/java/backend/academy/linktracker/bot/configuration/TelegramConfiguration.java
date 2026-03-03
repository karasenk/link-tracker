package backend.academy.linktracker.bot.configuration;

import backend.academy.linktracker.bot.commands.MagicBotCommand;
import backend.academy.linktracker.bot.properties.TelegramProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfiguration {
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public TelegramBot telegramBot(TelegramProperties properties) {
        var builder = new TelegramBot.Builder(properties.getToken())
                .apiUrl(properties.getUrl())
                .updateListenerSleep(properties.getUpdateListenerSleep().toMillis());

        if (properties.isDebug()) {
            builder.debug();
        }
        TelegramBot bot = builder.build();
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                String answerText = "Команда не найдена. Используйте /help чтобы узнать список доступных команд.";
                if (update.message() != null
                        && update.message().text() != null
                        && update.message().text().startsWith("/")) {
                    long chatId = update.message().chat().id();
                    String messageText = update.message().text();
                    for (String commandName : applicationContext.getBeanNamesForType(MagicBotCommand.class)) {
                        if (commandName.equals(messageText)) {
                            MagicBotCommand command = applicationContext.getBean(messageText, MagicBotCommand.class);
                            answerText = command.process();
                            break;
                        }
                    }
                    SendMessage sendMessage = new SendMessage(chatId, answerText);
                    bot.execute(sendMessage);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        return bot;
    }
}
