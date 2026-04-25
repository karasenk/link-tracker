package backend.academy.linktracker.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommandRegistry {

    private final Map<Command, MagicBotCommand> handlers;
    public CommandRegistry(List<MagicBotCommand> commands) {
        this.handlers = commands.stream()
            .collect(Collectors.toMap(
                MagicBotCommand::getCommand,
                Function.identity(),
                (a, b) -> {
                    throw new IllegalStateException(
                        "Конфликт: два обработчика для команды %s: %s и %s"
                            .formatted(a.getCommand(), a.getClass().getSimpleName(), b.getClass().getSimpleName())
                    );
                }
            ));

        log.atInfo()
            .addKeyValue("registeredCommands", handlers.keySet().stream()
                .map(Command::getName)
                .toList())
            .log("CommandRegistry initialized");
    }

    public Optional<MagicBotCommand> find(Command command) {
        return Optional.ofNullable(handlers.get(command));
    }
}
