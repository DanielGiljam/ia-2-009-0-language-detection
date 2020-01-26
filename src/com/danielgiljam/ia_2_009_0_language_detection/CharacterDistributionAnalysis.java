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

    static <T extends CharacterDistributionAnalysis> double getDifference(final T analysis1, final T analysis2) {
        // TODO implement getDifference()!
        return 0f;
    }
}
