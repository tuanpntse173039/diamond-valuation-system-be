package com.letitbee.diamondvaluationsystem.enums;

public enum Cut {
    FAIR("Fair"),
    GOOD("Good"),
    VERY_GOOD("Very Good"),
    EXCELLENT("Excellent"),
    IDEAL("Ideal"),
    ;

    private final String displayName;

    Cut(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Cut fromString(String displayName) {
        for (Cut cut : Cut.values()) {
            if (displayName.toUpperCase().contains(cut.getDisplayName().toUpperCase())) {
                return cut;
            }
        }
        return null;
    }

    public static double cutScore(String displayName) {
        Cut cut = fromString(displayName);
        if(cut == null) {
            return 0.0;
        }

        return switch (cut) {
            case FAIR -> 7.0;
            case GOOD -> 8.0;
            case VERY_GOOD -> 8.5;
            case EXCELLENT -> 9.0;
            case IDEAL -> 9.6;
            default -> 0.0;
        };
    }

}
