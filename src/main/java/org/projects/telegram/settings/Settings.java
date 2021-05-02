package org.projects.telegram.settings;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "countUniqueTask")
public class Settings {

    private int min;
    private int max;
    private int countList;
    private int countUniqueTask;

    public Settings(int min, int max, int countList) {
        this.min = calculateMin(min, max);
        this.max = calculateMax(min, max);
        this.countList = calculateCountList(countList);
        this.countUniqueTask = calculateCountUniqueTask(this.min, this.max);
    }

    static int calculateMin(int min, int max) {
        return Math.min(min, max);
    }

    static int calculateMax(int min, int max) {
        return Math.max(max, min);
    }

    static int calculateCountList(int listCount) {
        return Math.min(listCount, 5);
    }

    static int calculateCountUniqueTask(int min, int max) {
        int uniqueTaskCount = 0;
        int range = max - min;
        if (range > 0) {
            uniqueTaskCount = (range * (range + 1)) / 2;
        }
        if (uniqueTaskCount < 0) {
            throw new IllegalArgumentException(String.format("Заданный диапазон значений %s - %s слишком большой. ", min, max));
        }
        if (uniqueTaskCount == 0) {
            throw new IllegalArgumentException("Заданные числа совпадают! Попробуйте другие значения");
        }
        return uniqueTaskCount;
    }
}
