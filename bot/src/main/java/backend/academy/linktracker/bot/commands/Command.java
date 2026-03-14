package backend.academy.linktracker.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Command {
    START("/start", "Начало работы с Link Tracker"),
    HELP("/help", "Вывод списка доступных команд");

    private final String name;
    private final String description;
}
