package backend.academy.linktracker.scrapper.service;

import backend.academy.linktracker.scrapper.exception.ChatAlreadyExistsException;
import backend.academy.linktracker.scrapper.exception.ChatNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final List<Long> repository = new ArrayList<>();

    public boolean exists(Long chatId){
        return repository.contains(chatId);
    }
    public void register(Long chatId){
        if (exists(chatId)){
            throw new ChatAlreadyExistsException(chatId);
        }
        repository.add(chatId);
    }
    public void remove(Long chatId){
        if (!exists(chatId)){
            throw new ChatNotFoundException(chatId);
        }
        repository.remove(chatId);
    }
}
