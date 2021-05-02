package org.projects.telegram.commands.operations;

import org.projects.calculation.CalculationService;
import org.projects.calculation.Calculator;
import org.projects.enums.EnumOperation;
import org.projects.generatedFile.FileWordGeneration;
import org.projects.telegram.Bot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public abstract class CommandOperation extends BotCommand implements IBotCommand {

    private CalculationService service;

    CommandOperation(String command, String description) {
        super(command, description);
        this.service = new CalculationService(new FileWordGeneration(), new Calculator());
    }

    void sendAnswer(AbsSender absSender, Long chatId, List<EnumOperation> operations, String description) {
        try {
            absSender.execute(createDocument(chatId, operations, description));
        } catch (IOException | RuntimeException e) {
            sendError(absSender, chatId);
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendDocument createDocument(Long chatId, List<EnumOperation> operations, String fileName) throws IOException {
        FileInputStream stream = service.getResultFile(operations, Bot.getUserSettings(chatId));
        SendDocument document = new SendDocument();
        document.setChatId(chatId.toString());
        document.setDocument(new InputFile(stream, String.format("%s.docx", fileName)));
        return document;
    }

    private void sendError(AbsSender absSender, Long chatId) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Ошибка!"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public abstract void execute(AbsSender absSender, User user, Chat chat, String[] strings);

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        execute(absSender, message.getFrom(), message.getChat(), arguments);
    }
}
