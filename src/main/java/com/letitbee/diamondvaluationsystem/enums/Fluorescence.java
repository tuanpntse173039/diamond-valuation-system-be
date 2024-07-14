package com.letitbee.diamondvaluationsystem.enums;

public enum Fluorescence {
    NONE("None"),
    FAINT("Faint"),
    MEDIUM("Medium"),
    STRONG("Strong"),
    VERY_STRONG("Very Strong");

    private final String displayName;

    Fluorescence(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Fluorescence fromString(String displayName) {
        for (Fluorescence shape : Fluorescence.values()) {
            if (shape.getDisplayName().equalsIgnoreCase(displayName)) {
                return shape;
            }
        }
        return null;
    }
}
