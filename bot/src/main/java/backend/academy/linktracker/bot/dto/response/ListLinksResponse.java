package backend.academy.linktracker.bot.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
