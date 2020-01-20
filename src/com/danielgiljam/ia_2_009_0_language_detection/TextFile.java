package com.danielgiljam.ia_2_009_0_language_detection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

class TextFile {

    static final String TEXT_FILE_EXTENSION = ".txt";

    private static final String TEXT_FILE_NAME_PATTERN = "^.*\\.txt";

    private final String name;
    private final String text;

    TextFile(Path path) throws IOException {
        final String fileName = path.getFileName().toString();
        name = fileName.substring(0, fileName.length() - TEXT_FILE_EXTENSION.length());
        final StringBuilder stringBuilder = new StringBuilder();
        Files.readAllLines(path).forEach(line -> {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        });
        text = stringBuilder.toString();
    }

    String getName() {
        return name;
    }

    String getText() {
        return text;
    }

    static boolean isTextFile(Path path) {
        return Files.isRegularFile(path) && Pattern.matches(TEXT_FILE_NAME_PATTERN, path.toString());
    }
}
