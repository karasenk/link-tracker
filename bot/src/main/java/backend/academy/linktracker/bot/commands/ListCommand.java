package backend.academy.linktracker.bot.commands;

import backend.academy.linktracker.bot.client.ScrapperApiException;
import backend.academy.linktracker.bot.client.ScrapperClient;
import backend.academy.linktracker.bot.dto.response.LinkResponse;
import backend.academy.linktracker.bot.dto.response.ListLinksResponse;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class ListCommand implements MagicBotCommand {
    @Autowired
    ScrapperClient scrapperClient;
    @Override
    public SendMessage process(Update update){
        long chatId = update.message().chat().id();
        try {
            ListLinksResponse linksResponses = scrapperClient.getLinks(chatId);
            StringBuilder notificationText = new StringBuilder();
            if (linksResponses.getLinks() == null || linksResponses.getLinks().isEmpty()){
                notificationText.append("Нет ссылок для отслеживания.");
            } else {
                for (LinkResponse linkResponse : linksResponses.getLinks()){
                    notificationText.append(linkResponse.getUrl()).append("\n");
                }
            }
            return new SendMessage(chatId, notificationText.toString());
        } catch (ScrapperApiException e){
            handleError(e.getError(), e.getFunkName());
            return new SendMessage(chatId, Objects.requireNonNullElse(e.getError().getDescription(), "Что-то пошло не так."));
        }
    }
    @Override
    public Command getCommand(){
        return Command.LIST;
    }
}
