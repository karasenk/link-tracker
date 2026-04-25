package backend.academy.linktracker.scrapper.client.bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {
    @Bean
    public RestClient botRestClient() {
        return RestClient.builder()
            .baseUrl("http://localhost:8081")
            .build();
    }
}
