package backend.academy.linktracker.scrapper.client.external_update.github;

import backend.academy.linktracker.scrapper.properties.GithubProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Configuration
public class GitHubClientConfig {
    @Autowired
    GithubProperties properties;
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setInterceptors(List.of((request, body, execution) -> {
            request.getHeaders().add("Accept", "application/vnd.github+json");
            request.getHeaders().add("Authorization", "Bearer " + properties.getToken());
            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
