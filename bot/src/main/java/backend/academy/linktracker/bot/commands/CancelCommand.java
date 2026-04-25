package backend.academy.linktracker.bot.commands;

import backend.academy.linktracker.bot.state.State;
import backend.academy.linktracker.bot.state.UserSession;
import backend.academy.linktracker.bot.state.UserStateService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelCommand implements MagicBotCommand{
    @Autowired
    UserStateService userStateService;

    @Override
    public SendMessage process(Update update) {
        long chatId = update.message().chat().id();
        UserSession session = userStateService.getSession(chatId);
        State state = session.getState();
        if (state.equals(State.TYPING_LINK) || state.equals(State.TYPING_TAGS)) {
            userStateService.resetSession(chatId);
            return new SendMessage(chatId, "Добавление ссылки отменено.");
        }
        return new SendMessage(chatId, "Команда доступна только во время добавления ссылки.");
    }

    @Override
    public Command getCommand() {
        return Command.CANCEL;
    }
}
