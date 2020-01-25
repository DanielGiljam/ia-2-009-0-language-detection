package com.danielgiljam.ia_2_009_0_language_detection;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

abstract class CharacterDistributionAnalysis {

    private static final Pattern NON_LETTER_PATTERN = Pattern.compile("[^\\p{L} ]");

    private static final String SECOND_COLUMN_HEADER = "Procentuell fördelning";

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

        final int firstColumnHeaderWidth = firstColumnHeader.length();
        final int firstColumnWidth = Math.max(firstColumnHeader.length(), firstColumnCellContentMaxWidth);
        final String firstColumnCellTemplate = getFirstColumnCellTemplate(firstColumnHeaderWidth, firstColumnCellContentMaxWidth, firstColumnWidth);
        final String secondColumnCellTemplate = getSecondColumnCellTemplate(total);
        final int secondColumnWidth = Math.max(SECOND_COLUMN_HEADER.length(), getSecondColumnCellContent(secondColumnCellTemplate, total, total).length());
        final String rowTemplate = getRowTemplate(firstColumnWidth, secondColumnWidth);

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format(rowTemplate, firstColumnHeader, SECOND_COLUMN_HEADER));
        stringBuilder.append(String.format(rowTemplate, "", "").replace(" ", "-"));

        int count;
        for (final Map.Entry<String, Integer> entry : data.entrySet()) {
            count = entry.getValue();
            stringBuilder.append(String.format(
                    rowTemplate,
                    getFirstColumnCellContent(firstColumnCellTemplate, entry.getKey()),
                    getSecondColumnCellContent(secondColumnCellTemplate, count, total)
            ));
        }

        System.out.println(stringBuilder.toString());
    }

    private static String getFirstColumnCellTemplate(final int firstColumnHeaderWidth, final int firstColumnCellContentMaxWidth, final int firstColumnWidth) {
        final int dataLossCompensation = (firstColumnHeaderWidth % 2 != 0 || firstColumnCellContentMaxWidth % 2 != 0) ? 1 : 0;
        return firstColumnWidth != firstColumnCellContentMaxWidth ? String.format("%%%ds", firstColumnHeaderWidth / 2 + firstColumnCellContentMaxWidth / 2 + dataLossCompensation) : "%s";
    }

    private static String getFirstColumnCellContent(final String firstColumnCellTemplate, final String string) {
        return String.format(firstColumnCellTemplate, string);
    }

    private static String getSecondColumnCellTemplate(final int total) {
        return String.format("%%%ds/%d   ( %%5s %%%% )", Integer.toString(total).length(), total);
    }

    private static String getSecondColumnCellContent(final String secondColumnCellTemplate, final int count, final int total) {
        return String.format(secondColumnCellTemplate, count, String.format("%.2f", (double) count / total * 100));
    }

    private static String getRowTemplate(final int firstColumnWidth, final int secondColumnWidth) {
        return String.format("%%-" + firstColumnWidth + "s   %%-" + secondColumnWidth + "s%%n");
    }

    private static String removeNonLetters(final String characters) {
        return NON_LETTER_PATTERN.matcher(characters).replaceAll("");
    }
}
