package com.letitbee.diamondvaluationsystem.enums;

public enum Symmetry {
    FAIR("Fair"),
    GOOD("Good"),
    VERY_GOOD("Very Good"),
    EXCELLENT("Excellent");

    private final String displayName;

    Symmetry(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Symmetry fromString(String displayName) {
        for (Symmetry symmetry : Symmetry.values()) {
            if (symmetry.getDisplayName().equalsIgnoreCase(displayName)) {
                return symmetry;
            }
        }
        return null;
    }
}