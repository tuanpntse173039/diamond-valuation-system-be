package com.letitbee.diamondvaluationsystem.enums;

public enum Polish {
    FAIR("Fair"),
    GOOD("Good"),
    VERY_GOOD("Very Good"),
    EXCELLENT("Excellent");

    private final String displayName;

    Polish(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Polish fromString(String displayName) {
        for (Polish shape : Polish.values()) {
            if (shape.getDisplayName().equalsIgnoreCase(displayName)) {
                return shape;
            }
        }
        return null;
    }
}
