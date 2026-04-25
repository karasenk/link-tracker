package backend.academy.linktracker.bot.controller;

import backend.academy.linktracker.bot.dto.request.LinkUpdateRequest;
import backend.academy.linktracker.bot.service.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
public class UpdateController {
    @Autowired
    private TelegramService telegramService;

    @PostMapping
    public ResponseEntity<Void> update(@RequestBody LinkUpdateRequest request) {
        for (Long chatId : request.getTgChatIds()) {
            telegramService.sendMessage(
                chatId,
                "Обновление по ссылке:\n" +
                    request.getUrl() + "\n" +
                    request.getDescription()
            );
        }
        return ResponseEntity.ok().build();
    }
}
