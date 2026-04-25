package backend.academy.linktracker.scrapper.client.external_update.github;

import backend.academy.linktracker.scrapper.client.Source;
import backend.academy.linktracker.scrapper.client.external_update.ExternalUpdateClient;
import backend.academy.linktracker.scrapper.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;

@Component
public class GitHubUpdateClient implements ExternalUpdateClient {
    @Autowired
    GitHubNotificationClient client;
    @Override
    public List<Notification> getUpdates(long fromTimestamp, String link) {
        return client.getNotifications(link).stream()
            .map(n -> Notification.builder()
                .id(n.getId())
                .source(Source.GITHUB)
                .timestamp(Instant.parse(n.getUpdatedAt()).getEpochSecond())
                .build()
            ).filter(e -> e.getTimestamp() > fromTimestamp)
            .toList();
    }

    @Override
    public Source getSource() {
        return Source.GITHUB;
    }
}
