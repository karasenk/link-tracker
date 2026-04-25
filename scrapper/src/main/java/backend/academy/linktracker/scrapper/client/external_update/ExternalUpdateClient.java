package backend.academy.linktracker.scrapper.client.external_update;

import backend.academy.linktracker.scrapper.client.Source;
import backend.academy.linktracker.scrapper.dto.Notification;
import java.util.List;

public interface ExternalUpdateClient {
    List<Notification> getUpdates(long fromTimestamp, String link);

    Source getSource();
}
