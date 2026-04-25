package backend.academy.linktracker.bot.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {
    @Bean
    public RestClient scrapperRestClient() {
        return RestClient.builder()
            .baseUrl("http://localhost:8080")
            .build();
    }
}
