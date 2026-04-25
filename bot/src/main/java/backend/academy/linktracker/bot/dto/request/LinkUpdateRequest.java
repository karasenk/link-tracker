package backend.academy.linktracker.bot.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkUpdateRequest {
    private Long id;
    private String url;
    private String description;
    private List<Long> tgChatIds;
}
