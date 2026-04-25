package backend.academy.linktracker.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelegramService {
    @Autowired
    TelegramBot bot;

    public void sendMessage(long chatId, String text){
        bot.execute(new SendMessage(chatId, text));
    }
}
