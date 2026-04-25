package backend.academy.linktracker.scrapper.client.external_update.stackoverflow;

import backend.academy.linktracker.scrapper.client.Source;
import backend.academy.linktracker.scrapper.client.external_update.ExternalUpdateClient;
import backend.academy.linktracker.scrapper.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class StackOverflowUpdateClient implements ExternalUpdateClient {
    @Autowired
    StackOverflowClient client;
    @Override
    public List<Notification> getUpdates(long fromTimestamp, String link) {
        return client.getNotifications(fromTimestamp, link).stream()
            .map (n ->
                Notification.builder()
                    .id(n.getId())
                    .source(Source.STACKOVERFLOW)
                    .timestamp(n.getLastActivityDate())
                    .build()
            )
            .toList();
    }

    @Override
    public Source getSource() {
        return Source.STACKOVERFLOW;
    }
}
