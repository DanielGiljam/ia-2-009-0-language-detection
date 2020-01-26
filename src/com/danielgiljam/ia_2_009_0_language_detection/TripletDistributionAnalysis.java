package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TripletDistributionAnalysis extends CharacterDistributionAnalysis {

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("[\\s]");
    private static final Pattern TRIPLET_PATTERN = Pattern.compile(".{3}");

    TripletDistributionAnalysis(final String characters) {
        super(characters);
    }

    @Override
    protected void analyze() {
        final String characters = WHITESPACE_PATTERN.matcher(this.characters).replaceAll("");
        final Matcher matcher = TRIPLET_PATTERN.matcher(characters);
        String match;
        while (matcher.find(total)) {
            match = matcher.group().toLowerCase();
            data.put(match, data.containsKey(match) ? data.get(match) + 1 : 1);
            total++;
        }
    }

    @Override
    void print() {
        print(2);
    }
}
