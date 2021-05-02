package org.projects.telegram.commands.service;

import org.projects.Utils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand  extends ServiceCommand {

    public StartCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        sendAnswer(absSender, chat.getId(), this.getCommand(), userName,
                "Погнали! Если Вам нужна помощь, нажмите /help");
    }

    @Override
    public String getCommandIdentifier() {
        return "start";
    }
}
