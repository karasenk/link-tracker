package backend.academy.linktracker.bot.client;

import backend.academy.linktracker.bot.dto.request.AddLinkRequest;
import backend.academy.linktracker.bot.dto.request.RemoveLinkRequest;
import backend.academy.linktracker.bot.dto.response.ApiErrorResponse;
import backend.academy.linktracker.bot.dto.response.LinkResponse;
import backend.academy.linktracker.bot.dto.response.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Slf4j
@Service
public class ScrapperClient {
    @Autowired
    private RestClient restClient;

    public LinkResponse addLink(Long chatId, String link, List<String> tags) {
        AddLinkRequest request = new AddLinkRequest(link, tags, List.of());
        return restClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", chatId.toString())
            .body(request)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                (_, resp) -> {handleError(resp, "addLink");})
            .body(LinkResponse.class);
    }

    public void removeLink(Long chatId, String link) {
        RemoveLinkRequest request = new RemoveLinkRequest(link);
        restClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", chatId.toString())
            .body(request)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                (_, resp) -> {handleError(resp, "removeLink");});
    }

    public ListLinksResponse getLinks(Long chatId) {
        return restClient.get()
            .uri("/links")
            .header("Tg-Chat-Id", chatId.toString())
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                (_, resp) -> {handleError(resp, "getLinks");})
            .body(ListLinksResponse.class);
    }

    public void registerChat(Long chatId) {
        restClient.post()
            .uri("/tg-chat/{id}", chatId)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                (_, resp) -> {handleError(resp, "registerChat");});
    }

    public void deleteChat(Long chatId) {
        restClient.delete()
            .uri("/tg-chat/{id}", chatId)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                (_, resp) -> {handleError(resp, "deleteChat");})
            .toBodilessEntity();
    }
    private void handleError(ClientHttpResponse response, String funcName) {
        ApiErrorResponse error = null;
        try {
            error = new ObjectMapper().readValue(
                response.getBody(),
                ApiErrorResponse.class
            );

        } catch (Exception ex) {
            log.error("Failed to parse ApiErrorResponse", ex);
        }
        throw new ScrapperApiException(error, funcName);
    }
}
