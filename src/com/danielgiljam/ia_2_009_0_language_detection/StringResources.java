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

     static final String ERROR_MESSAGE_TEMPLATE = "%n%s: %s%n";

     static final String USAGE_MESSAGE_CRASH_VERSION =
             "Usage:\n" +
             "\tdetectlang <path>\n" +
             "\tdetectlang --pre-def-lang-dir=<path> <path>\n" +
             "\tdetectlang --help\n" +
             "\tdetectlang -v\n\n" +
             "Options:\n" +
             "\t-v, --version                 Shows version information about the tool.\n" +
             "\t-h, --help                    Shows usage information about the tool.\n" +
             "\t--pre-def-lang-dir=<path>,\n" +
             "\t--sample-data-dir=<path>      Let's you specify where the tool looks for\n" +
             "\t                              the text files it uses to build its\n" +
             "\t                              predefined language signatures.\n" +
             "\t                              If you don't specify this, the default.\n" +
             "\t                              is \"resources/sample-data\". The \"path\" -argument\n" +
             "\t                              may not contain any kind of whitespace\n" +
             "\t                              or attempted character escape.\n\n" +
             "The required \"path\" -argument should point to a text file that you want the tool to \"guess\" the language of.";

     static final String USAGE_MESSAGE =
             "IA-2-009 (0) Language Detection\n\n" +
             "Usage:\n" +
             "\tdetectlang <path>\n" +
             "\tdetectlang --pre-def-lang-dir=<path> <path>\n" +
             "\tdetectlang --help\n" +
             "\tdetectlang -v\n\n" +
             "Options:\n" +
             "\t-v, --version                 Shows version information about the tool.\n" +
             "\t-h, --help                    Shows usage information about the tool.\n" +
             "\t--pre-def-lang-dir=<path>,\n" +
             "\t--sample-data-dir=<path>      Let's you specify where the tool looks for\n" +
             "\t                              the text files it uses to build its\n" +
             "\t                              predefined language signatures.\n" +
             "\t                              If you don't specify this, the default.\n" +
             "\t                              is \"resources/sample-data\". The \"path\" -argument\n" +
             "\t                              may not contain any kind of whitespace\n" +
             "\t                              or attempted character escape.\n\n" +
             "This tool analyzes the distribution characters and substrings in text files\n" +
             "to create \"language signatures\" based on which it can \"guess\" what language\n" +
             "an arbitrary text file in written in. The required \"path\" -argument should point\n" +
             "to a text file that you want the tool to \"guess\" the language of.";

     static final String INFO_MESSAGE =
             "v1.0.0\n\n" +
             "© Daniel Giljam 2020\n" +
             "Det här CLI-verktyget är licensierat under MIT licensen.";
}
