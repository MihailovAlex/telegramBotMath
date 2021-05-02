package org.projects.telegram.settings;

import org.projects.telegram.Bot;

public class UserSettingsCommand {

    public String userCommandExecute(Long chatId, String text) {
        Settings settings;
        String answer;
        try {
            settings = createSettings(text);
            saveUserSettings(chatId, settings);
            answer = "��������� ��������. ��������� �������� � /settings";
        } catch (IllegalArgumentException e) {
            answer = e.getMessage() +
                    "\n ��������� �� ���� ��������. ��������� �������� � /settings";
        } catch (Exception e) {
            answer = "������ �����! ��������� ������������ ��������� ��������: ��� ����� ����� ������� ��� ������. \n" +
                    "��� �������������� /help";
        }
        return answer;
    }

    private Settings createSettings(String text) throws IllegalArgumentException {
        if (text == null) {
            throw new IllegalArgumentException("��������� �� �������� �������");
        }
        text = text.replaceAll(", ", ",")
                .replaceAll(" ", ",");
        String[] parameters = text.split(",");
        if (parameters.length != 3) {
            throw new IllegalArgumentException(String.format("�� ������� �������� �� \"%s\" 3 �����",
                    text));
        }
        int min = Integer.parseInt(parameters[0]);
        int max = Integer.parseInt(parameters[1]);
        int listCount = Integer.parseInt(parameters[2]);

        if (min == 0 || max == 0 || listCount == 0) {
            throw new IllegalArgumentException("�� ���� �� ���������� �� ������ ��������� 0");
        }

        return new Settings(min, max, listCount);
    }

    private void saveUserSettings(Long chatId, Settings settings) {
        if (!settings.equals(Bot.getDefaultSettings())) {
            Bot.getUserSettings().put(chatId, settings);
        } else {
            Bot.getUserSettings().remove(chatId);
        }
    }

}