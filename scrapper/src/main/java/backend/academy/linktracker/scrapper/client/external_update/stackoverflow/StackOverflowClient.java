package backend.academy.linktracker.scrapper.client.external_update.stackoverflow;

import backend.academy.linktracker.scrapper.dto.GitHubNotification;
import backend.academy.linktracker.scrapper.dto.StackoverflowNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class StackOverflowClient {
    @Autowired
    private RestTemplate restTemplate;

    public List<StackoverflowNotification> getNotifications(long fromDate, String link) {
        ResponseEntity<List<StackoverflowNotification>> response =
            restTemplate.exchange(
                link + "?order=desc&site=stackoverflow&fromdate=" + fromDate,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StackoverflowNotification>>() {}
            );

        return response.getBody();
    }
}
