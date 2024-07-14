package com.letitbee.diamondvaluationsystem.enums;

public enum Cut {
    FAIR("Fair", 7),
    GOOD("Good", 8),
    VERY_GOOD("Very Good", 8.5),
    EXCELLENT("Excellent", 9.6),
    IDEAL("Ideal", 10.0);


    private final String displayName;
    private final double score;

    Cut(String displayName, double score) {
        this.displayName = displayName;
        this.score = score;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getScore() {
        return score;
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
        return (cut != null) ? cut.getScore() : 0.0;  // Return 0.0 if the cut is not found
    }
}
