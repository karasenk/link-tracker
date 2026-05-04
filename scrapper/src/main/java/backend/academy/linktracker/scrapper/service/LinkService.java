package backend.academy.linktracker.scrapper.service;

import backend.academy.linktracker.scrapper.dto.request.AddLinkRequest;
import backend.academy.linktracker.scrapper.dto.request.RemoveLinkRequest;
import backend.academy.linktracker.scrapper.dto.response.LinkResponse;
import backend.academy.linktracker.scrapper.dto.response.ListLinksResponse;
import backend.academy.linktracker.scrapper.exception.ChatNotFoundException;
import backend.academy.linktracker.scrapper.exception.IncorrectLinkException;
import backend.academy.linktracker.scrapper.exception.LinkAlreadyExistsException;
import backend.academy.linktracker.scrapper.exception.LinkNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static backend.academy.linktracker.scrapper.service.UpdateAggregatorService.GITHUB_PATTERN;
import static backend.academy.linktracker.scrapper.service.UpdateAggregatorService.STACKOVERFLOW_PATTERN;

@Service
@Slf4j
public class LinkService {
    @Autowired
    private ChatService chatService;
    private final Map<Long, List<LinkResponse>> linkRepository = new ConcurrentHashMap<>();

    public ListLinksResponse getLinks(Long chatId) {
        if (!chatService.exists(chatId)){
            throw new ChatNotFoundException(chatId);
        }
        List<LinkResponse> links = linkRepository.get(chatId);

        return ListLinksResponse.builder()
            .links(links)
            .size(links.size())
            .build();
    }

    public LinkResponse addLink(Long chatId, AddLinkRequest request) {
        if (!chatService.exists(chatId)){
            throw new ChatNotFoundException(chatId);
            }
        if (exists(chatId, request.getLink())){
            throw new LinkAlreadyExistsException(request.getLink());
        }
        if (!GITHUB_PATTERN.matcher(request.getLink()).matches() &&
            !STACKOVERFLOW_PATTERN.matcher(request.getLink()).matches()){
            throw new IncorrectLinkException(
                request.getLink(),
                List.of(
                    GITHUB_PATTERN.pattern(),
                    STACKOVERFLOW_PATTERN.pattern()
                )
                );
        }
        List<LinkResponse> linkList = linkRepository.computeIfAbsent(chatId, id -> new ArrayList<>());
        LinkResponse link = LinkResponse.builder()
            .url(request.getLink())
            .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
            .tags(request.getTags())
            .filters(request.getFilters())
            .build();

        linkList.add(link);
        log.info("Link {} for user {} added", link.getUrl(), chatId);
        return link;
    }

    public LinkResponse removeLink(Long chatId, RemoveLinkRequest request) {
        if (!chatService.exists(chatId)){
            throw new ChatNotFoundException(chatId);
        }
        if (!exists(chatId, request.getLink())){
            throw new LinkNotFoundException(request.getLink());
        }
        List<LinkResponse> links = linkRepository.get(chatId);

        LinkResponse removed = null;
        for (LinkResponse linkResponse : links){
            if (linkResponse.getUrl().equals(request.getLink())){
                removed = linkResponse;
                links.remove(linkResponse);
                break;
            }
        }
        return removed;
    }
    public Map<String, List<Long>> getLinkUsers(){
        Map<String, List<Long>> linkToUsers = new HashMap<>();
        for (Map.Entry<Long, List<LinkResponse>> userToLinks : linkRepository.entrySet()){
            Long chatId = userToLinks.getKey();
            for (LinkResponse linkResponse : userToLinks.getValue()){
                String link = linkResponse.getUrl();
                if (linkToUsers.containsKey(link)){
                    linkToUsers.get(link).add(chatId);
                } else {
                    linkToUsers.put(link, List.of(chatId));
                }
            }
        }
        return linkToUsers;
    }
    private boolean exists(Long chatId, String link){
        List<LinkResponse> linkList = linkRepository.computeIfAbsent(chatId, id -> new ArrayList<>());
        for (LinkResponse linkResponse : linkList){
            if (linkResponse.getUrl().equals(link)){
                return true;
            }
        }
        return false;
    }
}
