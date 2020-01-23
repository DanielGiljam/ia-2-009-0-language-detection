package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.HashMap;

class LanguageSignature {

    private final HashMap<String, CharacterDistributionAnalysis> analytics = new HashMap<>();

    LanguageSignature(final TextFile textFile) {
        final String characters = textFile.getText();
        analytics.put(SingleLetterDistributionAnalysis.class.getSimpleName(), new SingleLetterDistributionAnalysis(characters));
        analytics.put(TripletDistributionAnalysis.class.getSimpleName(), new TripletDistributionAnalysis(characters));
        analytics.put(LeadingLettersInWordsAnalysis.class.getSimpleName(), new LeadingLettersInWordsAnalysis(characters));
    }
}
