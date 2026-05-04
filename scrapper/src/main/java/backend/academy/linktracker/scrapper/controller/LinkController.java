package backend.academy.linktracker.scrapper.controller;

import backend.academy.linktracker.scrapper.dto.request.AddLinkRequest;
import backend.academy.linktracker.scrapper.dto.request.RemoveLinkRequest;
import backend.academy.linktracker.scrapper.dto.response.LinkResponse;
import backend.academy.linktracker.scrapper.dto.response.ListLinksResponse;
import backend.academy.linktracker.scrapper.service.LinkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/links")
    public ResponseEntity<@NotNull ListLinksResponse> getLinks(
        @RequestHeader("Tg-Chat-Id") Long chatId
    ) {
        return ResponseEntity.ok(linkService.getLinks(chatId));
    }

    @PostMapping("/links")
    public ResponseEntity<@NotNull LinkResponse> addLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody AddLinkRequest request
    ) {
        return ResponseEntity.ok(linkService.addLink(chatId, request));
    }

    @DeleteMapping("/links")
    public ResponseEntity<@NotNull LinkResponse> removeLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody RemoveLinkRequest request
    ) {
        return ResponseEntity.ok(linkService.removeLink(chatId, request));
    }
}
