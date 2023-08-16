package com.playground;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public class Application {

    private final ClassLoader classLoader = getClass().getClassLoader();

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

    public void start() {
        File file = getFileFromResource("app");

        System.out.println("File Path: " + file.getAbsolutePath());

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            System.out.println("File Content: \n");

            lines.forEach(System.out::println);
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private @NotNull File getFileFromResource(String fileName) {
        URL resource = classLoader.getResource(fileName);

        if (Objects.isNull(resource)) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        File file = new File(resource.getFile());

        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        return file;
    }
}
