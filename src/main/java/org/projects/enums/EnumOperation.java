package org.projects.enums;

import java.util.Arrays;
import java.util.List;

public enum EnumOperation {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION;

    public static List<EnumOperation> getOperation() {
        return Arrays.asList(values());
    }
}
