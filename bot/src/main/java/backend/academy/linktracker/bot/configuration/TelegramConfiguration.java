package backend.academy.linktracker.bot.configuration;

import backend.academy.linktracker.bot.commands.CommandRegistry;
import backend.academy.linktracker.bot.properties.TelegramProperties;
import backend.academy.linktracker.bot.state.UserStateService;
import com.pengrad.telegrambot.TelegramBot;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.Proxy;

@Configuration
public class TelegramConfiguration {
    @Autowired
    UserStateService userStateService;
    @Autowired
    CommandRegistry registry;
    @Bean
    public TelegramBot telegramBot(TelegramProperties properties) {
        var builder = new TelegramBot.Builder(properties.getToken())
                .apiUrl(properties.getUrl())
                .updateListenerSleep(properties.getUpdateListenerSleep().toMillis())
                .okHttpClient(new OkHttpClient.Builder().proxy(Proxy.NO_PROXY).build());

        if (properties.isDebug()) {
            builder.debug();
        }
        TelegramBot bot = builder.build();
        bot.setUpdatesListener(new UpdatesListenerImpl(userStateService, registry, bot));
        return bot;
    }
}
