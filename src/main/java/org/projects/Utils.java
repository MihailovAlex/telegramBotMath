package org.projects;

import org.telegram.telegrambots.meta.api.objects.User;

public class Utils {

    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
    }
}
