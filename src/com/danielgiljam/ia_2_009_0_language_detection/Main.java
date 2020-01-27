package com.danielgiljam.ia_2_009_0_language_detection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Pattern SAMPLE_DATA_OPTION_PATTERN = Pattern.compile("^(?:--pre-def-lang-dir|--sample-data-dir)=([\\S]+)$");

    private static final String DEFAULT_PATH_TO_SAMPLE_DATA = "resources/sample-data";

    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_1 = "Expected at least one argument.";
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_2 = "Expected argument to be a path to a text file.";
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_3 = "Command contained illegal arguments.";

    public static void main(final String[] args) {
        final Args argsParsed = new Args(args);
        if (argsParsed.printInfo) {
            System.out.println();
            System.out.println(StringResources.INFO_MESSAGE);
            return;
        }
        if (argsParsed.printUsage) {
            System.out.println();
            System.out.println(StringResources.USAGE_MESSAGE);
            return;
        }
        try {
            final List<TextFile> sampleDataFiles = getSampleData(argsParsed.pathToSampleData);
            final List<LanguageSignature> predefinedLanguageSignatures = sampleDataFiles
                .stream()
                .map((final TextFile textFile) -> new LanguageSignature(textFile, textFile.getName()))
                .collect(Collectors.toList());
            final TextFile inputFile = getInputFile(argsParsed.pathToInputFile);
            final LanguageSignature inputFileSignature = new LanguageSignature(inputFile);
            System.out.println();
            inputFileSignature.print();
            inputFileSignature.scoreAgainst(predefinedLanguageSignatures);
        } catch (final IOException|IllegalArgumentException error) {
            crash(error);
        }
    }

    private static List<TextFile> getSampleData(final String pathToSampleData) throws IOException {
        final Path sampleDataDirectory = Paths.get(pathToSampleData);
        if (Files.isDirectory(sampleDataDirectory)) {
            try (final Stream<Path> sampleDataFilesStream = Files.walk(sampleDataDirectory)) {
                return sampleDataFilesStream.filter(TextFile::isTextFile)
                        .map((final Path path) -> {
                            try {
                                return new TextFile(path);
                            } catch (final IOException error) {
                                crash(error);
                            }
                            return null;
                        })
                        .collect(Collectors.toList());
            }
        } else throw new DirectoryNotFoundException(sampleDataDirectory);
    }

    private static TextFile getInputFile(final String pathToInputFile) throws IOException, IllegalArgumentException {
        if (pathToInputFile == null) throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_2);
        final Path inputFilePath = Paths.get(pathToInputFile);
        if (!TextFile.isTextFile(inputFilePath)) throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_2);
        return new TextFile(inputFilePath);
    }

    private static void crash(final Exception error) {
        System.err.println(String.format(
                StringResources.ERROR_MESSAGE_TEMPLATE,
                error.getClass().getSimpleName(),
                error.getMessage()
        ));
        System.out.println(StringResources.USAGE_MESSAGE_CRASH_VERSION);
        System.exit(1);
    }

    private static class Args {

        private boolean printInfo = false;
        private boolean printUsage = false;
        private String pathToSampleData = DEFAULT_PATH_TO_SAMPLE_DATA;
        private String pathToInputFile;

        private Args(final String[] args) {
            switch (args.length) {
                case 0:
                    crash(new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_1));
                    break;
                case 1:
                    parseSingle(args[0]);
                    break;
                case 2:
                    parseTwin(args[0], args[1]);
                    break;
                default:
                    crash(new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_3));

            }
        }

        private void parseSingle(final String arg) {
            switch (arg) {
                case "-v":
                case "--version":
                    printInfo = true;
                    break;
                case "-h":
                case "--help":
                    printUsage = true;
                    break;
                default:
                    pathToSampleData = DEFAULT_PATH_TO_SAMPLE_DATA;
                    pathToInputFile = arg;
            }
        }

        private void parseTwin(final String arg1, final String arg2) {
            Matcher matcher = SAMPLE_DATA_OPTION_PATTERN.matcher(arg1);
            if (matcher.matches()) {
                pathToSampleData = matcher.group(1);
                pathToInputFile = arg2;
                return;
            }
            matcher = SAMPLE_DATA_OPTION_PATTERN.matcher(arg2);
            if (matcher.matches()) {
                pathToSampleData = matcher.group(1);
                pathToInputFile = arg1;
                return;
            }
            crash(new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_3));
        }
    }

    private static class DirectoryNotFoundException extends FileNotFoundException {

        private static final String MESSAGE_TEMPLATE = "The path \"%s\" is not a path to a directory.";

        DirectoryNotFoundException(final Path path) {
            super(String.format(MESSAGE_TEMPLATE, path.toString()));
        }
    }
}
