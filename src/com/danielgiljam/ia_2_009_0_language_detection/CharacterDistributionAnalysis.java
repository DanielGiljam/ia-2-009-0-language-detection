package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

abstract class CharacterDistributionAnalysis {

    private static final Pattern NON_LETTER_PATTERN = Pattern.compile("[^\\p{L} ]");

    protected final String characters;
    protected final HashMap<String, Integer> data = new HashMap<>();
    protected int total = 0;

    CharacterDistributionAnalysis(final String characters) {
        this.characters = removeNonLetters(characters);
        analyze();
    }

    abstract protected void analyze();

    abstract void print();

    protected void print(final int firstColumnCellContentMaxWidth) {

        final int firstColumnHeaderWidth = Math.max(StringResources.FIRST_COLUMN_HEADER_PART_1.length(), StringResources.FIRST_COLUMN_HEADER_PART_2.length());
        final int firstColumnWidth = Math.max(firstColumnHeaderWidth, firstColumnCellContentMaxWidth);
        final String secondColumnCellTemplate = getSecondColumnCellTemplate(total);
        final int secondColumnWidth = Math.max(StringResources.SECOND_COLUMN_HEADER.length(), getSecondColumnCellContent(secondColumnCellTemplate, total, total).length());
        final String rowTemplate = getRowTemplate(firstColumnWidth, secondColumnWidth);

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(StringResources.FIRST_COLUMN_HEADER_PART_1 + "\n");
        stringBuilder.append(String.format(
                rowTemplate,
                StringResources.FIRST_COLUMN_HEADER_PART_2,
                StringResources.SECOND_COLUMN_HEADER
        ));
        stringBuilder.append(getDivider(rowTemplate));

        int count;
        for (final Map.Entry<String, Integer> entry : data.entrySet()) {
            count = entry.getValue();
            stringBuilder.append(String.format(
                    rowTemplate,
                    entry.getKey(),
                    getSecondColumnCellContent(secondColumnCellTemplate, count, total)
            ));
        }

        stringBuilder.append(getDivider(rowTemplate));

        System.out.println(stringBuilder.toString());
    }

    static <T extends CharacterDistributionAnalysis> double getDifference(final T analysis1, final T analysis2) {
        final HashMap<String, Double> data1 = getNormalizedData(analysis1);
        final HashMap<String, Double> data2 = getNormalizedData(analysis2);
        final HashMap<String, Double> difference = maskDataSets(data1, data2);
        final HashMap<String, Double> absoluteDifference = getAbsoluteData(difference);
        double accumulator = 0;
        for (final Double value : absoluteDifference.values()) accumulator += value;
        return accumulator / absoluteDifference.size();
    }

    private static String getDivider(final String rowTemplate) {
        return String.format(rowTemplate, "", "").replace(" ", "-");
    }

    private static String getSecondColumnCellTemplate(final int total) {
        return String.format("%%%ds/%d   ( %%%ds %%%% )", Integer.toString(total).length(), total, 5); // 5 = maximum characters needed to print a percentage (e.g. "99.99")
    }

    private static String getSecondColumnCellContent(final String secondColumnCellTemplate, final int count, final int total) {
        return String.format(secondColumnCellTemplate, count, String.format("%.2f", (double) count / total * 100));
    }

    private static String getRowTemplate(final int firstColumnWidth, final int secondColumnWidth) {
        return String.format("%%-%ds   %%-5%ds%%n", firstColumnWidth, secondColumnWidth);
    }

    private static String removeNonLetters(final String characters) {
        return NON_LETTER_PATTERN.matcher(characters).replaceAll("");
    }

    private static HashMap<String, Double> getNormalizedData(final CharacterDistributionAnalysis characterDistributionAnalysis) {
        final int total = characterDistributionAnalysis.total;
        final HashMap<String, Double> normalizedData = new HashMap<>();
        characterDistributionAnalysis.data.forEach((final String key, final Integer value) ->
                normalizedData.put(key, (double) value / total)
        );
        return normalizedData;
    }

    private static HashMap<String, Double> getAbsoluteData(final HashMap<String, Double> data) {
        final HashMap<String, Double> absoluteData = new HashMap<>();
        data.forEach((final String key, final Double value) ->
                absoluteData.put(key, Math.abs(value))
        );
        return absoluteData;
    }

    private static HashMap<String, Double> maskDataSets(final HashMap<String, Double> target, final HashMap<String, Double> mask) {
        final HashMap<String, Double> maskedDataSet = new HashMap<>(target);
        mask.forEach((final String key, final Double value) ->
                maskedDataSet.put(key, maskedDataSet.containsKey(key) ? maskedDataSet.get(key) - value : value * -1)
        );
        return maskedDataSet;
    }
}
