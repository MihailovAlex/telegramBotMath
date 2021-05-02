package org.projects.calculation;

import lombok.AllArgsConstructor;
import org.projects.enums.EnumOperation;
import org.projects.generatedFile.FileWordGeneration;
import org.projects.telegram.settings.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class CalculationService {

    private final FileWordGeneration fileWordGeneration;
    private final Calculator calculator;

    public FileInputStream getResultFile(List<EnumOperation> operations, Settings settings)
            throws IOException {
        List<String> taskList = new ArrayList<>();

        for (int i = 1; i <= settings.getCountList(); i++) {
            taskList.addAll(getTaskList(operations, settings));
        }
        if (taskList.isEmpty()) {
            throw new IllegalArgumentException(String.format("Ошибка! По заданным настройкам " +
                    "(min = %s, max = %s, listCount = %s) не удалось создать ни одной строки " +
                    "с примерами", settings.getMin(), settings.getMax(), settings.getCountList()));
        }
        return fileWordGeneration.createWordFile(taskList);
    }

    private List<String> getTaskList(List<EnumOperation> operations, Settings settings) {
        int taskCount = getOperationTaskCount(operations.size());
        List<String> taskList = new ArrayList<>();
        for (EnumOperation operation : operations) {
            fillTaskList(taskList, operation, taskCount, settings.getMin(), settings.getMax(),
                    settings.getCountUniqueTask());
        }
        Collections.shuffle(taskList);
        return taskList;
    }

    private void fillTaskList(List<String> taskList, EnumOperation operation, int taskCount, int min, int max,
                              int uniqueTaskCount) {
        if (taskCount <= uniqueTaskCount) {
            taskList.addAll(calculator.getTaskSet(operation, min, max, taskCount));
        } else {
            taskList.addAll(calculator.getTaskSet(operation, min, max, uniqueTaskCount));
            fillTaskList(taskList, operation, taskCount - uniqueTaskCount, min, max, uniqueTaskCount);
        }
    }

    private int getOperationTaskCount(int operationsCount) {
        int linesCount = 20;  //количество строк, вмещающихся на одну страницу А4
        if (operationsCount == 1) {
            return linesCount;
        } else if (operationsCount == 4) {
            return linesCount / 4;
        } else {
            throw new IllegalArgumentException("Количество операций для формирования файла с примерами больше 1");
        }
    }

}
