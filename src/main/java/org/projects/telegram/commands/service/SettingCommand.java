package org.projects.telegram.commands.service;

import org.projects.Utils;
import org.projects.telegram.Bot;
import org.projects.telegram.settings.Settings;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SettingCommand extends ServiceCommand {

    public SettingCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        Long chatId = chat.getId();
        Settings settings = Bot.getUserSettings(chatId);
        sendAnswer(absSender, chatId, this.getCommand(), userName,
                String.format("������������� ��������: \n" +
                                "- ����������� �����, �������������� � �������� - *%s*\n" +
                                "- ������������ �����, �������������� � �������� - *%s*\n" +
                                "- ����� ������� ��������� ����� - *%s*\n" +
                                "���� �� ������� �������� ��� ��������, ������� ����� ������� ��� ������ 3 ����� - " +
                                "����������� �����, ������������ ����� � ���������� ������� � ����� (�� ����� 5)\n" +
                                "��������, 1,70,1 ��� 2 20 2",
                        settings.getMin(), settings.getMax(), settings.getCountList()));
    }

    @Override
    public String getCommandIdentifier() {
        return "settings";
    }
}
