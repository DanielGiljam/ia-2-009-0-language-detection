package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LeadingLettersInWordsAnalysis extends CharacterDistributionAnalysis {

    private static final Pattern PATTERN = Pattern.compile("(?<=^|[\\s])\\S");

    LeadingLettersInWordsAnalysis(final String characters) {
        super(characters);
    }

    @Override
    protected void analyze() {
        final Matcher matcher = PATTERN.matcher(characters);
        String match;
        while (matcher.find()) {
            match = matcher.group().toLowerCase();
            data.put(match, data.containsKey(match) ? data.get(match) + 1 : 1);
            total++;
        }
    }

    @Override
    void print() {
        print(1);
    }
}
