package backend.academy.linktracker.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StackoverflowNotification {
    @JsonProperty("question_id")
    private Long id;

    @JsonProperty("last_activity_date")
    private long lastActivityDate;
}
