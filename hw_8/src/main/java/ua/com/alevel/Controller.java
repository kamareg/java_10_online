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
            case "3" -> deleteFileOrDirectory(directory);
            case "4" -> moveFileOrDirectory(directory);
            case "5" -> searchFileOrDirectory(directory);
            case "6" -> searchTextInFileOrDirectory(directory);
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
        } else if (checkSpecialSymbols(fileName)) {
            createFileOrDirectory(directory, isFile, chosenType, fileName);
        } else {
            System.out.println("Name can't contain some special symbols");
        }
    }

    private boolean checkSpecialSymbols(String fileName) {
        return !fileName.matches(".*[/\\\\:*?<>\"|].*");
    }

    private void createFileOrDirectory(File directory, boolean isFile, String chosenType, String fileName) {
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
    }

    private void deleteFileOrDirectory(File directory) throws IOException {
        readDirectory(directory);
        System.out.println("What file or directory do you want to delete?");
        File file = searching(directory);
        if (!file.getAbsolutePath().equals(directory.getAbsolutePath())) {
            if (file.delete()) {
                System.out.println("Was deleted successfully");
            } else {
                System.out.println("Something wrong");
            }
        }
    }

    private void moveFileOrDirectory(File directory) throws IOException {
        readDirectory(directory);
        System.out.println("What file or folder do you want to move?");
        String oldFileName = reader.readLine();
        File oldFile = new File(directory.getAbsolutePath() + File.separator + oldFileName);
        if (!oldFileName.isBlank() && oldFile.exists() && checkSpecialSymbols(oldFileName)) {
            System.out.println("Enter new directory full path");
            String newDirectoryName = reader.readLine();
            File newFile = new File(newDirectoryName + File.separator + oldFileName);
            if (!newFile.exists() && new File(newDirectoryName).isDirectory()) {
                if (oldFile.renameTo(newFile)) {
                    System.out.println("Successfully moved");
                } else {
                    System.out.println("Something wrong");
                }
            } else {
                System.out.println("File is already exist or such directory was not found");
            }
        } else {
            System.out.println("Such file or directory doesn't exist");
        }
    }

    private void searchFileOrDirectory(File directory) throws IOException {
        System.out.println("What file do we need to find?");
        File file = searching(directory);
        if (!file.getAbsolutePath().equals(directory.getAbsolutePath())) {
            System.out.println("File was found: " + file);
        }
    }

    private File searching(File directory) throws IOException {
        String fileName = reader.readLine();
        for (File file : directory.listFiles()) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        System.out.println("File or directory doesn't exist");
        return directory;
    }

    private void searchTextInFileOrDirectory(File directory) throws IOException {
        if (directory.listFiles().length == 0) {
            System.out.println("Directory is empty");
        } else {
            System.out.println("What text do we need to find?");
            String fileName = reader.readLine();
            for (File file : directory.listFiles()) {
                if (file.getName().contains(fileName)) {
                    System.out.println(file);
                }
            }
        }
    }
}
