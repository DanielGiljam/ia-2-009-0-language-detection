package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.regex.Pattern;

class LeadingLettersInWordsAnalysis extends CharacterDistributionAnalysis {

    private static final Pattern PATTERN = Pattern.compile("(?<=^|[\\s])\\S");

    LeadingLettersInWordsAnalysis(final String characters) {
        super(characters);
    }

    @Override
    protected void analyze() {
        // TODO: implement analyze() in LeadingLettersInWordsAnalysis!
    }

    @Override
    void print() {
        print("1:a tecken", 1);
    }
}
