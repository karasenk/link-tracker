package backend.academy.linktracker.bot.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserStateService {
    private static final Map<Long, UserSession> userSessions = new ConcurrentHashMap<>();

    public UserSession getSession(Long id){
        return userSessions.computeIfAbsent(id, _ -> {
            log.atInfo()
                .addKeyValue("chatId", id)
                .log("New session created");
            return new UserSession();
        });
    }
    public void resetSession(Long id){
        userSessions.remove(id);
        log.atInfo()
            .addKeyValue("chatId", id)
            .log("Session reset");
    }
}
