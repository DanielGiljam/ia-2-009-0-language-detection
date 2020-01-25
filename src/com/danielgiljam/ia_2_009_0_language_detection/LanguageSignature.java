package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.*;

class LanguageSignature {

    private String language;
    private final HashMap<String, CharacterDistributionAnalysis> analytics = new HashMap<>();

    LanguageSignature(final TextFile textFile, final String predefinedLanguage) {
        final String characters = textFile.getText();
        analytics.put(SingleLetterDistributionAnalysis.class.getSimpleName(), new SingleLetterDistributionAnalysis(characters));
        analytics.put(TripletDistributionAnalysis.class.getSimpleName(), new TripletDistributionAnalysis(characters));
        analytics.put(LeadingLettersInWordsAnalysis.class.getSimpleName(), new LeadingLettersInWordsAnalysis(characters));
        language = predefinedLanguage;
    }

    LanguageSignature(final TextFile textFile) {
        this(textFile, null);
    }

    private int scoreAgainst(final LanguageSignature languageSignature) {
        // TODO: implement scoreAgainst()!
        return 0;
    }

    HashMap<String, Integer> scoreAgainst(final List<LanguageSignature> languageSignatures) {

        final StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(String.format("Comparing %s against %s", language == null ? "unknown signature" : String.format("signature for %s", language), languageSignatures.get(0).language));
        for (int i = 1; i < languageSignatures.size() - 1; i++) stringBuilder1.append(String.format(", %s", languageSignatures.get(i).language));
        stringBuilder1.append(String.format(" and %s.%n", languageSignatures.get(languageSignatures.size() - 1).language));
        System.out.println(stringBuilder1.toString());

        int longestLanguageName = 0;
        int languageNameLength;
        String language;
        final HashMap<String, Integer> scores = new HashMap<>();
        for (final LanguageSignature signature : languageSignatures) {
            language = signature.language;
            scores.put(signature.language, scoreAgainst(signature));
            languageNameLength = language.length();
            if (languageNameLength > longestLanguageName) longestLanguageName = languageNameLength;
        }

        final List<Map.Entry<String, Integer>> scoresList = new ArrayList<>(scores.entrySet());
        scoresList.sort(Comparator.comparingInt(Map.Entry::getValue));

        // TODO: print more elaborate table!
        final String languageNameTemplate = "%s:";
        final String rowTemplate = String.format("%%-%ds %%d%n", longestLanguageName + 1);
        final StringBuilder stringBuilder2 = new StringBuilder();
        for (final Map.Entry<String, Integer> score : scoresList) {
            stringBuilder2.append(String.format(rowTemplate, String.format(languageNameTemplate, score.getKey()), score.getValue()));
        }
        System.out.println(stringBuilder2.toString());

        return scores;
    }

    void print() {
        analytics.values().forEach(CharacterDistributionAnalysis::print);
    }
}
