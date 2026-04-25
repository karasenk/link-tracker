package backend.academy.linktracker.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Command {
    START("/start", "Начало работы с Link Tracker"),
    HELP("/help", "Список доступных команд"),
    LIST("/list", "Список всех ссылок, отслеживаемых пользователем"),
    TRACK("/track", "Начать отслеживание ссылки. Опционально можно указать теги для ссылки."),
    CANCEL("/cancel", "Отменить добавление ссылки. Команда доступна только в процессе добавления ссылки."),
    UNTRACK("/untrack", "Прекратить отслеживание ссылки.");

    private final String name;
    private final String description;

    public static Optional<Command> fromText(String text) {
        return Arrays.stream(values())
            .filter(command -> command.name.equals(text))
            .findFirst();
    }
}
