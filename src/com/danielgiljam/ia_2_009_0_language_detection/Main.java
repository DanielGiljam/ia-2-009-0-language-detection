package com.danielgiljam.ia_2_009_0_language_detection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String PATH_TO_SAMPLE_DATA = "resources/sample-data";

    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "The tool expects the first argument to be a path to a text file.";

    public static void main(final String[] args) throws IOException {
        final List<TextFile> sampleDataFiles = getSampleData();
        final List<LanguageSignature> predefinedLanguageSignatures = sampleDataFiles
                .stream()
                .map((final TextFile textFile) -> new LanguageSignature(textFile, textFile.getName()))
                .collect(Collectors.toList());
        final TextFile inputFile = getInputFile(args);
        final LanguageSignature inputFileSignature = new LanguageSignature(inputFile);
        System.out.println();
        inputFileSignature.print();
        inputFileSignature.scoreAgainst(predefinedLanguageSignatures);
        System.out.println(StringResources.INFO_MESSAGE);
    }

    private static List<TextFile> getSampleData() throws IOException {
        final Path sampleDataDirectory = Paths.get(PATH_TO_SAMPLE_DATA);
        if (Files.isDirectory(sampleDataDirectory)) {
            try (final Stream<Path> sampleDataFilesStream = Files.walk(sampleDataDirectory)) {
                return sampleDataFilesStream.filter(TextFile::isTextFile)
                        .map((final Path path) -> {
                            try {
                                return new TextFile(path);
                            } catch (IOException error) {
                                error.printStackTrace();
                                System.exit(1);
                            }
                            return null;
                        })
                        .collect(Collectors.toList());
            }
        } else throw new DirectoryNotFoundException(sampleDataDirectory);
    }

    private static TextFile getInputFile(final String[] args) throws IOException {
        if (args.length == 0) throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        final Path inputFilePath = Paths.get(args[0]);
        return new TextFile(inputFilePath);
    }

    private static class DirectoryNotFoundException extends FileNotFoundException {

        private static final String MESSAGE_TEMPLATE = "The path \"%s\" is not a path to a directory.";

        DirectoryNotFoundException(final Path path) {
            super(String.format(MESSAGE_TEMPLATE, path.toString()));
        }
    }
}
