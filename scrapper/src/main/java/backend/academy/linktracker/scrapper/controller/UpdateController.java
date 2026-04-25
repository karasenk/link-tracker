package backend.academy.linktracker.scrapper.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {
    @Scheduled
    @PostMapping("/updates")
    public void sendUpdates(){

    }
}
