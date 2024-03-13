package ua.com.alevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void start() {
        try {
            printStartMenu();
            String userInput = reader.readLine();
            while (!userInput.equals("0")) {
                String directoryPath = userInput.length() > 0 ? userInput : File.separator;
                File directory = new File(directoryPath);
                if (checkDirectory(directory)) {
                    System.out.println("Current directory is " + directory.getAbsolutePath());
                    printMainMenu();
                    processHandler(reader.readLine(), directory);
                } else {
                    throw new RuntimeException("Wrong path or such directory doesn't exist");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printStartMenu() {
        System.out.println();
        System.out.println("Please enter your directory path");
        System.out.println("Otherwise press enter and we'll start from the root");
        System.out.println("If you want to exit please enter 0");
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("What do you want to do in the current directory?");
        System.out.println("1.View a list of files and folders");
        System.out.println("2.Create a file or folder");
        System.out.println("3.Delete a file or folder");
        System.out.println("4.Move a file or folder");
        System.out.println("5.Search for a file or folder");
        System.out.println("6.Search for text in all files and folders.");
        System.out.println("7.Enter another directory or exit.");
    }

    private boolean checkDirectory(File directory) throws IOException {
        return directory.exists() && directory.isDirectory();
    }

    private void processHandler(String input, File directory) throws IOException {
        switch (input) {
            case "1" -> readDirectory(directory);
            case "2" -> createFileOrDirectory(directory);
            case "7" -> throw new RuntimeException("Change directory or exit");
        }
    }

    private void readDirectory(File directory) {
        if (directory.listFiles().length > 0) {
            System.out.println("List of files and folders in the current directory:");
            for (File file : directory.listFiles()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(file.isDirectory() ? "- " : "--> ");
                String shortPath = file.getAbsolutePath().substring(directory.getAbsolutePath().length());
                stringBuilder.append(formatPath(shortPath));
                System.out.println(stringBuilder);
            }
        } else {
            System.out.println("Directory is empty");
        }
    }

    private String formatPath(String path) {
        if (path.startsWith(File.separator) && path.length() > 1) {
            return path.substring(1);
        }
        return path;
    }

    private void createFileOrDirectory(File directory) throws IOException {
        System.out.println("What do you want to do in the current directory?");
        System.out.println("1.Create a file.");
        System.out.println("2.Create a folder.");
        switch (reader.readLine()) {
            case "1" -> createFileOrDirectory(directory, true);
            case "2" -> createFileOrDirectory(directory, false);
            default ->
                    System.out.println("Your input is wrong. You'll be redirected to the main menu");
        }
    }

    private void createFileOrDirectory(File directory, boolean isFile) throws IOException {
        String chosenType = isFile ? "file" : "directory";
        System.out.println("Enter your " + chosenType + " name");
        String fileName = reader.readLine();
        if (fileName.isBlank()) {
            System.out.println("Empty " + chosenType + " name");
        } else if (!fileName.matches(".*[/\\\\:*?<>\"|].*")) {
            File file = new File(directory.getAbsolutePath() + File.separator + fileName);
            try {
                if (isFile ? file.createNewFile() : file.mkdir()) {
                    System.out.println("A " + chosenType + " was created");
                } else {
                    System.out.println("A " + chosenType + " is already exists");
                }
            } catch (IOException e) {
                System.out.println("A " + chosenType + " wasn't created: " + e.getMessage());
            }
        } else {
            System.out.println("A " + chosenType + " name can't contain some special symbols");
        }
    }


}
