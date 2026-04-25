package backend.academy.linktracker.bot.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddLinkRequest {
    private String link;
    private List<String> tags;
    private List<String> filters;
}
