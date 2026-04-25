package backend.academy.linktracker.scrapper.client.bot;

import backend.academy.linktracker.scrapper.dto.request.LinkUpdateRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class BotClient {
    @Autowired
    private RestClient restClient;

    public void sendUpdate(Long id, String url, String description, List<Long> chatIds){
        restClient.post()
            .uri("/updates")
            .body(
                LinkUpdateRequest.builder()
                    .id(id)
                    .url(url)
                    .description(description)
                    .tgChatIds(chatIds)
            )
            .retrieve()
            .toBodilessEntity();
    }
}
