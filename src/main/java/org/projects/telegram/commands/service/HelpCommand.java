package org.projects.telegram.commands.service;

import org.projects.Utils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand {

    public HelpCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        sendAnswer(absSender, chat.getId(), this.getCommand(), userName,
                "При помощи команды /settings вы можете указать желаемый диапазон " +
                        "значений чисел (минимальное и максимальное число), а также количество страниц" +
                        "в итогов документе. Для этого введите 3 числа через пробел или запятую.\n\n" +
                        "Затем при помощи команд /addition (сложение), /subtraction (вычитание)," +
                        "/multi (умножение) и /division (деление) выбираете нужную операцию и получаете" +
                        "документ в формате .docx с примерами на выбранную арифметическую операцию.\n\n" +
                        "Каждая страница итогового документа содержит по 20 уникальных примеров. Также " +
                        "(в целях предотвращения больших задержек системы) максимальное количество страниц " +
                        "в документе ограничено 5-ю страницами.");
    }

    @Override
    public String getCommandIdentifier() {
        return "help";
    }
}
