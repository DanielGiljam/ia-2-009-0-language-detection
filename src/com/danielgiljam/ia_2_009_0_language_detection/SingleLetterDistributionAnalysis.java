package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.regex.Pattern;

class SingleLetterDistributionAnalysis extends CharacterDistributionAnalysis {

    private static final Pattern PATTERN = Pattern.compile("[.]");

    SingleLetterDistributionAnalysis(final String characters) {
        super(characters);
    }

    @Override
    protected void analyze() {
        // TODO: implement analyze() in SingleLetterDistributionAnalysis!
    }

    @Override
    void print() {
        print("Tecken", 1);
    }
}
