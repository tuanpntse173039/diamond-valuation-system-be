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
            if (cut.getDisplayName().equalsIgnoreCase(displayName)) {
                return cut;
            }
        }
        return null;
    }

    public static double cutScore(String displayName) {
        Cut cut = fromString(displayName);
        switch (cut) {
            case FAIR:
                return 7.0;
            case GOOD:
                return 8.0;
            case VERY_GOOD:
                return 8.5;
            case EXCELLENT:
                return 9.0;
            case IDEAL:
                return 9.6;
            default:
                return 0.0;
        }
    }

}
