package backend.academy.linktracker.scrapper.controller;

import backend.academy.linktracker.scrapper.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tg-chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/{id}")
    public ResponseEntity<@NotNull Map<String, Object>> registerChat(@PathVariable Long id) {
        chatService.register(id);
        Map<String, Object> body = new HashMap<>();
        body.put("description", "Чат зарегистрирован");
        log.atInfo().log("Chat " + id + " registered");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NotNull Map<String, Object>> deleteChat(@PathVariable Long id) {
        chatService.remove(id);
        Map<String, Object> body = new HashMap<>();
        body.put("description", "Чат успешно удалён");
        log.atInfo().log("Chat " + id + " deleted");
        return ResponseEntity.ok(body);
    }
}
