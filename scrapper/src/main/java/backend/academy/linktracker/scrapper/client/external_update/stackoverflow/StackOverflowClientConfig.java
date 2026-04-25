package backend.academy.linktracker.scrapper.client.external_update.stackoverflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class StackOverflowClientConfig {

    @Bean
    public RestTemplate stackOverflowRestTemplate() {
        return new RestTemplate();
    }
}
