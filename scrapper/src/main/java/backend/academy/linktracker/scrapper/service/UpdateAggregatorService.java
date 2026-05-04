package backend.academy.linktracker.scrapper.service;

import backend.academy.linktracker.scrapper.client.Source;
import backend.academy.linktracker.scrapper.client.external_update.ExternalUpdateClient;
import backend.academy.linktracker.scrapper.client.external_update.github.GitHubUpdateClient;
import backend.academy.linktracker.scrapper.client.external_update.stackoverflow.StackOverflowUpdateClient;
import backend.academy.linktracker.scrapper.dto.Notification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Service
public class UpdateAggregatorService {
    public static final Pattern GITHUB_PATTERN = Pattern.compile(
        "^https://github\\.com/([A-Za-z0-9_.-]+)/([A-Za-z0-9_.-]+)(/.*)?$"
    );

    public static final Pattern STACKOVERFLOW_PATTERN = Pattern.compile(
        "^https://stackoverflow\\.com/questions/(\\d+)(/.*)?$"
    );

    private final Map<Source, Long> lastTimestamps = new ConcurrentHashMap<>();

    public List<Notification> getAllUpdates(String link) {
        ExternalUpdateClient client;
        if (GITHUB_PATTERN.matcher(link).matches()){
            client = new GitHubUpdateClient();
        } else {
            client = new StackOverflowUpdateClient();
        }

        long last = lastTimestamps.getOrDefault(client.getSource(), 0L);
        List<Notification> updates = client.getUpdates(last, link);

        updates.forEach(u ->
            lastTimestamps.merge(
                client.getSource(),
                u.getTimestamp(),
                Math::max
            )
        );
        return updates;
    }
}
