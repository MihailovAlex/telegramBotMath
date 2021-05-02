package org.projects.telegram;

import lombok.Getter;
import org.projects.telegram.commands.operations.AdditionCommand;
import org.projects.telegram.commands.operations.DivisionCommand;
import org.projects.telegram.commands.operations.MultiplicationCommand;
import org.projects.telegram.commands.operations.SubtractionCommand;
import org.projects.telegram.commands.service.HelpCommand;
import org.projects.telegram.commands.service.SettingCommand;
import org.projects.telegram.commands.service.StartCommand;
import org.projects.telegram.settings.Settings;
import org.projects.telegram.settings.UserSettingsCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingCommandBot {

    @Getter
    private static Map<Long, Settings> userSettings;

    @Getter
    private static final Settings defaultSettings = new Settings(1, 10, 1);
    private final UserSettingsCommand userSettingsCommand;

    public Bot() {
        super();
        this.userSettingsCommand = new UserSettingsCommand();
        register(new AdditionCommand("plus", "Сложение"));
        register(new SubtractionCommand("minus", "Вычитание"));
        register(new MultiplicationCommand("multi", "Умножение"));
        register(new DivisionCommand("division", "Деление"));
        register(new StartCommand("start", "Старт"));
        register(new HelpCommand("help", "Помощь"));
        register(new SettingCommand("settings", "Мои настройки"));
        userSettings = new HashMap<>();

    }


    public String getBotUsername() {
        return "Duck_duck_bot";
    }

    public String getBotToken() {
        return "1445653628:AAG3Zag6iFOBAC0U_1FNjN1McsaRr7yOkNw";
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();

        String answer = userSettingsCommand.userCommandExecute(chatId, msg.getText());
        setAnswer(chatId, answer);
    }

    public static Settings getUserSettings(Long chatId) {
        Map<Long, Settings> userSettings = Bot.getUserSettings();
        Settings settings = userSettings.get(chatId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }

    private void setAnswer(Long chatId, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
