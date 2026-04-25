package backend.academy.linktracker.bot.configuration;

import backend.academy.linktracker.bot.commands.Command;
import backend.academy.linktracker.bot.commands.CommandRegistry;
import backend.academy.linktracker.bot.state.State;
import backend.academy.linktracker.bot.state.UserStateService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class UpdatesListenerImpl implements UpdatesListener {
    private final UserStateService userStateService;
    private final CommandRegistry registry;
    private final TelegramBot bot;

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() == null
                || update.message().text() == null) {
                continue;
            }
            String messageText = update.message().text().strip();
            log.atInfo()
                .addKeyValue("chatId", update.message().chat().id())
                .addKeyValue("username", update.message().chat().username())
                .addKeyValue("message", messageText)
                .log("Update received");
            SendMessage sendMessage;
            if (messageText.startsWith("/")) {
                sendMessage = handleCommand(update);
            } else {
                sendMessage = handleText(update);
            }
            bot.execute(sendMessage);
            log.atInfo()
                .addKeyValue("chatId", update.message().chat().id())
                .addKeyValue("username", update.message().chat().username())
                .addKeyValue("message", sendMessage.getText())
                .log("Answer sent");
        }
        return CONFIRMED_UPDATES_ALL;
    }
    private SendMessage handleCommand(Update update){
        long chatId = update.message().chat().id();
        String messageText = update.message().text().strip();
        beforeCommandHandle(chatId, messageText);

        String command = update.message().text().strip();
        return Command.fromText(command)
            .flatMap(registry::find)
            .map(processor -> processor.process(update))
            .orElseGet(() -> new SendMessage(chatId, "Команда не найдена. Воспользуйтесь /help чтобы узнать список доступных команд."));

    }
    private SendMessage handleText(Update update){
        long chatId = update.message().chat().id();
        if (!userStateService.getSession(chatId).getState().equals(State.NO)){
            return registry.find(Command.TRACK).get().process(update);
        }
        return new SendMessage(chatId, "Взаимодействуйте с ботом с помощью команд, список команд: /help");
    }
    private void beforeCommandHandle(long chatId, String command){
        State state = userStateService.getSession(chatId).getState();
        if (!command.equals(Command.CANCEL.getName()) && (state.equals(State.TYPING_LINK) || state.equals(State.TYPING_TAGS))) {
            userStateService.resetSession(chatId);
        }
    }
}
