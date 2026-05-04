package backend.academy.linktracker.scrapper.service;

import backend.academy.linktracker.scrapper.client.bot.BotClient;
import backend.academy.linktracker.scrapper.dto.GitHubNotification;
import backend.academy.linktracker.scrapper.dto.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UniversalPoller {
    @Autowired
    private UpdateAggregatorService service;
    @Autowired
    private BotClient botClient;
    @Autowired
    private LinkService linkService;

    @Scheduled(fixedDelay = 60000)
    public void poll() {
        for (Map.Entry<String, List<Long>> linkUsers : linkService.getLinkUsers().entrySet()){
            String link = linkUsers.getKey();
            for (Notification notification : service.getAllUpdates(link)){
                botClient.sendUpdate(notification.getId(), link, "timestamp: " + notification.getTimestamp(), linkUsers.getValue());
            }
            log.info("Notifications for link {} sent", link);
        }
    }
}
