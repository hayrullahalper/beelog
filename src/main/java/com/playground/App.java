package com.playground;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void start() {
        File file = getFile("app");

        try {
            String content = Files.readString(file.toPath());
            System.out.println(content);
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private File getFile(String fileName)
    {
        File file = new File(Objects.requireNonNull(this.getClass()
                        .getClassLoader()
                        .getResource(fileName))
                .getFile());

        if (!file.exists()) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return file;
    }
}
