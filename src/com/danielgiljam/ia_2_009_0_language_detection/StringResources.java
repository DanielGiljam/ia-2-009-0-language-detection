package com.danielgiljam.ia_2_009_0_language_detection;

public class StringResources {

     static final String FIRST_COLUMN_HEADER_PART_1 = "Glyf/";
     static final String FIRST_COLUMN_HEADER_PART_2 = "sträng";
     static final String SECOND_COLUMN_HEADER = "Förekomst";

     static final String ANALYSIS_HEADER_TEMPLATE = "%s (%s)%n";

     static final String ANALYSIS_1 = "Enskilda tecken";
     static final String ANALYSIS_2 = "Tripletter";
     static final String ANALYSIS_3 = "Första tecknet i ett ord";

     static final String SCORE_TABLE_HEADER_PART_1_TEMPLATE = "Jämför %s mot %s";
     static final String SCORE_TABLE_HEADER_PART_2_TEMPLATE = ", %s";
     static final String SCORE_TABLE_HEADER_PART_3_TEMPLATE = " och %s.%n";
     static final String SCORE_TABLE_HEADER_KNOWN_SIGNATURE_TEMPLATE = "språksignatur för %s";
     static final String SCORE_TABLE_UNKNOWN_SIGNATURE = "okänd språksignatur";

     static final String SCORE_TABLE_FIRST_COLUMN_HEADER = "Språk";
     static final String SCORE_TABLE_LAST_COLUMN_HEADER = "Kombinerat";
     static final String SCORE_TABLE_COLUMN_HEADER_TEMPLATE = "Analys %d";

     static final String SCORE_TABLE_SUMMARY_TEMPLATE = "%nSpråksignaturen påminner mest om %s.%n";

     static final String INFO_MESSAGE =
             "v1.0.0\n\n" +
             "© Daniel Giljam 2020\n" +
             "Det här CLI-verktyget är licensierat under MIT licensen.";
}
