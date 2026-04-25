package backend.academy.linktracker.scrapper.controller;

import backend.academy.linktracker.scrapper.dto.request.AddLinkRequest;
import backend.academy.linktracker.scrapper.dto.request.RemoveLinkRequest;
import backend.academy.linktracker.scrapper.dto.response.LinkResponse;
import backend.academy.linktracker.scrapper.dto.response.ListLinksResponse;
import backend.academy.linktracker.scrapper.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/links")
    public ListLinksResponse getLinks(
        @RequestHeader("Tg-Chat-Id") Long chatId
    ) {
        return linkService.getLinks(chatId);
    }

    @PostMapping("/links")
    public LinkResponse addLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody AddLinkRequest request
    ) {
        return linkService.addLink(chatId, request);
    }

    @DeleteMapping("/links")
    public LinkResponse removeLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody RemoveLinkRequest request
    ) {
        return linkService.removeLink(chatId, request);
    }
}
