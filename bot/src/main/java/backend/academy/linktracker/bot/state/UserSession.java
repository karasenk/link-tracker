package backend.academy.linktracker.bot.state;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserSession {
    private State state = State.NO;
    private String link;
    private List<String> tags;
}
