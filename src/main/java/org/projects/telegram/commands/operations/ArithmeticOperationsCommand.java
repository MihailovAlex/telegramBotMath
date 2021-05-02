package org.projects.telegram.commands.operations;

import org.projects.enums.EnumOperation;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ArithmeticOperationsCommand extends CommandOperation  {
    public ArithmeticOperationsCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendAnswer(absSender, chat.getId(), EnumOperation.getOperation(), this.getDescription());
    }

    @Override
    public String getCommandIdentifier() {
        return "all";
    }
}
