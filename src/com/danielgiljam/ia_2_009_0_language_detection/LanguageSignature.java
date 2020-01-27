package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.*;

class LanguageSignature {

    private String language;
    private final HashMap<String, CharacterDistributionAnalysis> analytics = new HashMap<>();

    LanguageSignature(final TextFile textFile, final String predefinedLanguage) {
        final String characters = textFile.getText();
        analytics.put(StringResources.ANALYSIS_1, new SingleLetterDistributionAnalysis(characters));
        analytics.put(StringResources.ANALYSIS_2, new TripletDistributionAnalysis(characters));
        analytics.put(StringResources.ANALYSIS_3, new LeadingLettersInWordsAnalysis(characters));
        language = predefinedLanguage;
    }

    LanguageSignature(final TextFile textFile) {
        this(textFile, null);
    }

    private Score scoreAgainst(final LanguageSignature languageSignature) {
        final List<Double> scores = new ArrayList<>();
        for (final String key : new String[]{StringResources.ANALYSIS_1, StringResources.ANALYSIS_2, StringResources.ANALYSIS_3}) {
            scores.add(CharacterDistributionAnalysis.getDifference(this.analytics.get(key), languageSignature.analytics.get(key)));
        }
        return new Score(scores);
    }

    void scoreAgainst(final List<LanguageSignature> languageSignatures) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format(
                StringResources.SCORE_TABLE_HEADER_PART_1_TEMPLATE,
                language == null
                        ? StringResources.SCORE_TABLE_UNKNOWN_SIGNATURE
                        : String.format(StringResources.SCORE_TABLE_HEADER_KNOWN_SIGNATURE_TEMPLATE, language),
                languageSignatures.get(0).language
        ));
        for (int i = 1; i < languageSignatures.size() - 1; i++) stringBuilder.append(String.format(
                StringResources.SCORE_TABLE_HEADER_PART_2_TEMPLATE,
                languageSignatures.get(i).language
        ));
        stringBuilder.append(String.format(
                StringResources.SCORE_TABLE_HEADER_PART_3_TEMPLATE,
                languageSignatures.get(languageSignatures.size() - 1).language
        ));

        System.out.println(stringBuilder.toString());

        final HashMap<String, Score> scores = new HashMap<>();
        int firstColumnWidth = StringResources.SCORE_TABLE_FIRST_COLUMN_HEADER.length();
        String language;
        for (final LanguageSignature signature : languageSignatures) {
            language = signature.language;
            scores.put(signature.language, scoreAgainst(signature));
            if (language.length() > firstColumnWidth) firstColumnWidth = language.length();
        }

        final List<Map.Entry<String, Score>> scoresList = new ArrayList<>(scores.entrySet());
        scoresList.sort(Comparator.comparingDouble((final Map.Entry<String, Score> entry) -> entry.getValue().average));

        // 7 = maximum characters needed to print a percentage (e.g. "99.99 %")
        final int lastColumnWidth = Math.max(StringResources.SCORE_TABLE_LAST_COLUMN_HEADER.length(), 7);
        final int columnWidth = Math.max(String.format(StringResources.SCORE_TABLE_COLUMN_HEADER_TEMPLATE, 1).length(), 7);
        final String rowTemplate = getRowTemplate(firstColumnWidth, lastColumnWidth, columnWidth);

        stringBuilder = new StringBuilder();

        stringBuilder.append(String.format(
                rowTemplate,
                StringResources.SCORE_TABLE_FIRST_COLUMN_HEADER,
                String.format(StringResources.SCORE_TABLE_COLUMN_HEADER_TEMPLATE, 1),
                String.format(StringResources.SCORE_TABLE_COLUMN_HEADER_TEMPLATE, 2),
                String.format(StringResources.SCORE_TABLE_COLUMN_HEADER_TEMPLATE, 3),
                StringResources.SCORE_TABLE_LAST_COLUMN_HEADER
        ));
        stringBuilder.append(getDivider(rowTemplate));

        Score score;
        for (final Map.Entry<String, Score> scoreListItem : scoresList) {
            score = scoreListItem.getValue();
            stringBuilder.append(String.format(
                    rowTemplate,
                    scoreListItem.getKey(),
                    formatAsPercentage(score.get(1)),
                    formatAsPercentage(score.get(2)),
                    formatAsPercentage(score.get(3)),
                    formatAsPercentage(score.average)
            ));
        }

        stringBuilder.append(getDivider(rowTemplate));

        System.out.println(stringBuilder.toString());
    }

    void print() {
        final List<Map.Entry<String, CharacterDistributionAnalysis>> analyticsList = new ArrayList<>(analytics.entrySet());
        analyticsList.sort(Map.Entry.comparingByKey(
                (final String key1,
                 final String key2) -> key1.equals(StringResources.ANALYSIS_1) ? -1
                                        : key1.equals(StringResources.ANALYSIS_3) ? 1
                                        : key2.equals(StringResources.ANALYSIS_1) ? 1
                                        : -1
        ));
        int i = 1;
        for (final Map.Entry<String, CharacterDistributionAnalysis> entry : analyticsList) {
            System.out.println(String.format(
                    StringResources.ANALYSIS_HEADER_TEMPLATE,
                    entry.getKey().toUpperCase(),
                    String.format(StringResources.SCORE_TABLE_COLUMN_HEADER_TEMPLATE, i)
            ));
            entry.getValue().print();
            i++;
        }
    }

    private static String formatAsPercentage(final double content) {
        return String.format("%.2f %%", content * 100);
    }

    private static String getDivider(final String rowTemplate) {
        return String.format(
                rowTemplate,
                "",
                "",
                "",
                "",
                ""
        ).replace(" ", "-");
    }

    private static String getRowTemplate(final int firstColumnWidth, final int lastColumnWidth, final int columnWidth) {
        return String.format("%%-%ds   %%-%ds   %%-%ds   %%-%ds   %%-%ds%n", firstColumnWidth, columnWidth, columnWidth, columnWidth, lastColumnWidth);
    }

    private static class Score {

        private final List<Double> scores;
        private final double average;

        private Score(final List<Double> scores) {
            this.scores = scores;
            double accumulator = 0;
            for (final Double score : scores) accumulator += score;
            this.average = accumulator / scores.size();
        }

        private double get(final int index) {
            return scores.get(index - 1);
        }
    }
}
