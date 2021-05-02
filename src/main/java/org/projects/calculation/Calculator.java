package org.projects.calculation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.projects.enums.EnumOperation;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Calculator {

    Set<String> getTaskSet(EnumOperation operation, int min, int max, int count) {
        Set<String> tasks = new HashSet<>();
        while (tasks.size() < count) {
            addTaskToSet(tasks, operation, min, max);
        }
        return tasks;
    }

    private void addTaskToSet(Set<String> tasks, EnumOperation operation, int min, int max) {
        int first = getRandomIntBetweenRange(min, max);
        int second = getRandomIntBetweenRange(min, max);

        int result = calculate(operation, first, second);
        if (result >= min && result <= max) {
            tasks.add(generateTask(operation, first, second));
        }
    }

    private int getRandomIntBetweenRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private int calculate(EnumOperation operation, int first, int second) {
        switch (operation) {
            case ADDITION:
                return first + second;
            case SUBTRACTION:
                return first - second;
            case MULTIPLICATION:
                return first * second;
            case DIVISION:
            default:
                if (first < second) {
                    int a = first;
                    first = second;
                    second = a;
                }
                first = first - (first % second);
                return first / second;

        }
    }

    private String generateTask(EnumOperation operation, int first, int second) {
        switch (operation) {
            case ADDITION:
                return String.format("%s + %s =", first, second);
            case SUBTRACTION:
                return String.format("%s - %s =", first, second);
            case MULTIPLICATION:
                return String.format("%s * %s =", first, second);
            case DIVISION:
            default:
                if (first < second) {
                    int a = first;
                    first = second;
                    second = a;
                }
                first = first - (first % second);
                return String.format("%s : %s =", first, second);
        }
    }
}
