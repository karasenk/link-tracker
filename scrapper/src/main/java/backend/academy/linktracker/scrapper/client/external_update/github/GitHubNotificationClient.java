package backend.academy.linktracker.scrapper.client.external_update.github;

import backend.academy.linktracker.scrapper.client.external_update.ExternalUpdateClient;
import backend.academy.linktracker.scrapper.dto.GitHubNotification;
import backend.academy.linktracker.scrapper.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class GitHubNotificationClient {
    @Autowired
    private RestTemplate restTemplate;

    public List<GitHubNotification> getNotifications(String link) {
        ResponseEntity<List<GitHubNotification>> response =
            restTemplate.exchange(
                link,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GitHubNotification>>() {}
            );

        return response.getBody();
    }
}
