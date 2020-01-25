package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

abstract class CharacterDistributionAnalysis {

    private static final Pattern NON_LETTER_PATTERN = Pattern.compile("[^\\p{L} ]");

    private static final String SECOND_COLUMN_HEADER = "Procentuell fördelning";
    private static final String SECOND_COLUMN_CELL_CONTENT_TEMPLATE = "%d/%d (%.2f%%)";

    protected final String characters;
    protected final HashMap<String, Integer> data = new HashMap<>();
    protected int total = 0;

    CharacterDistributionAnalysis(final String characters) {
        this.characters = removeNonLetters(characters);
        analyze();
    }

    abstract protected void analyze();

    abstract void print();

    protected void print(final String firstColumnHeader, final int firstColumnCellContentMaxWidth) {
        final int firstColumnWidth = Math.max(firstColumnHeader.length(), firstColumnCellContentMaxWidth);
        final int secondColumnWidth = Math.max(SECOND_COLUMN_HEADER.length(), getSecondColumnCellContent(total, total).length());
        final String emptyString = "";
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("| %-" + firstColumnWidth + "s | %-" + secondColumnWidth + "s |%n", firstColumnHeader, SECOND_COLUMN_HEADER));
        stringBuilder.append(String.format("|%-" + (firstColumnWidth + 2) + "s|%-" + (secondColumnWidth + 2) + "s|%n", emptyString, emptyString).replace(" ", "-"));
        for (final Map.Entry<String, Integer> entry : data.entrySet()) {
            stringBuilder.append(String.format("|%-" + (firstColumnWidth + 2) + "s|%-" + (secondColumnWidth + 2) + "s|%n", entry.getKey(), entry.getValue()));
        }
        System.out.println(stringBuilder.toString());
    }

    private static String getSecondColumnCellContent(final int count, final int total) {
        return String.format(SECOND_COLUMN_CELL_CONTENT_TEMPLATE, count, total, (double) count / total);
    }

    private static String removeNonLetters(final String characters) {
        return NON_LETTER_PATTERN.matcher(characters).replaceAll("");
    }
}
