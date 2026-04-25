package backend.academy.linktracker.scrapper.dto;

import backend.academy.linktracker.scrapper.client.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Notification {
    private Long id;
    private Source source;
    private long timestamp;
}
