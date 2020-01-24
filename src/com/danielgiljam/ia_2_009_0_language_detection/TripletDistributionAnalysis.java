package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.regex.Pattern;

class TripletDistributionAnalysis extends CharacterDistributionAnalysis {

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("[\\s]");
    private static final Pattern TRIPLET_PATTERN = Pattern.compile("[.]{3}");

    TripletDistributionAnalysis(final String characters) {
        super(characters);
    }

    @Override
    protected void analyze() {
        // TODO: implement analyze() in TripletDistributionAnalysis!
    }

    @Override
    void print() {
        print("3-tecken kombination", 2);
    }
}
