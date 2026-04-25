package backend.academy.linktracker.bot.dto.response;

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
public class LinkResponse {
    private Long id;
    private String url;
    private List<String> tags;
    private List<String> filters;
}
