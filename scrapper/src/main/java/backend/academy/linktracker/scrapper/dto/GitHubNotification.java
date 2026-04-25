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
public class GitHubNotification {
    private Long id;
    @JsonProperty("updated_at")
    private String updatedAt;
}
