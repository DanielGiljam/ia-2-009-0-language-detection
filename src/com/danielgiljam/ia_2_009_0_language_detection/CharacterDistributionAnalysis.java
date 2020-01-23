package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.regex.Pattern;

abstract class CharacterDistributionAnalysis {

    private static final Pattern NON_LETTER_PATTERN = Pattern.compile("[^\\p{L} ]");

    protected String characters;

    CharacterDistributionAnalysis(final String characters) {
        this.characters = removeNonLetters(characters);
        analyze();
    }

    abstract protected void analyze();

    void print() {
        // TODO: implement print()!
    }

    private static String removeNonLetters(final String characters) {
        return NON_LETTER_PATTERN.matcher(characters).replaceAll("");
    }
}
