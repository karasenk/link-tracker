package backend.academy.linktracker.bot.client;

import backend.academy.linktracker.bot.dto.request.AddLinkRequest;
import backend.academy.linktracker.bot.dto.request.RemoveLinkRequest;
import backend.academy.linktracker.bot.dto.response.ApiErrorResponse;
import backend.academy.linktracker.bot.dto.response.LinkResponse;
import backend.academy.linktracker.bot.dto.response.ListLinksResponse;
import backend.academy.linktracker.bot.state.State;
import backend.academy.linktracker.bot.state.UserSession;
import backend.academy.linktracker.bot.state.UserStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ScrapperClient {
    @Autowired
    private RestClient restClient;

    @Autowired
    UserStateService userStateService;

    public LinkResponse addLink(Long chatId, String link, List<String> tags) {
        AddLinkRequest request = new AddLinkRequest(link, tags, List.of());
        return restClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", chatId.toString())
            .body(request)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                (_, resp) -> {
                    userStateService.resetSession(chatId);
                    handleError(resp, "addLink");})
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
                (_, resp) -> {handleError(resp, "removeLink");})
            .toBodilessEntity();
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
                (_, resp) -> {handleError(resp, "registerChat");})
            .toBodilessEntity();
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
        try {
            ApiErrorResponse error = new ObjectMapper().readValue(
                response.getBody(),
                ApiErrorResponse.class
            );
            throw new ScrapperApiException(error, funcName);

        } catch (IOException ex) {
            log.atError()
                .addKeyValue("message", ex.getMessage())
                .addKeyValue("class", ex.getClass())
                .log("Failed to parse ApiErrorResponse");
        }
    }
}
