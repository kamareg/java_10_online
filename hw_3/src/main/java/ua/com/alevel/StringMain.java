package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringMain {
    public static void main(String[] args) throws IOException {
        while (true) {
            menu();
        }
    }

    public static void menu() throws IOException {
        System.out.println("Please chose option:");
        System.out.println("1. Simple reverse.");
        System.out.println("2. Reverse by specified substring in a string.");
        System.out.println("3. Reverse by indexes of the beginning and end of a substring in a string.");
        System.out.println("0. Exit.");
        userAction();
    }

    public static void userAction() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String choice = reader.readLine();
        switch (choice) {
            case "1" -> simpleReverseUserAction();
            case "2" -> substringReverseUserAction();
            case "3" -> indexesReverseUserAction();
            case "0" -> System.exit(0);
            default -> menu();
        }
    }

    public static void simpleReverseUserAction() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String src = reader.readLine();
        System.out.println("Output: " + reverse(src));
    }

    public static void substringReverseUserAction() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String src = reader.readLine();
        System.out.println("Enter substring: ");
        String dest = reader.readLine();
        System.out.println("Output: " + reverse(src, dest));
    }

    public static void indexesReverseUserAction() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String src = reader.readLine();
        System.out.println("Enter first Index: ");
        String first = reader.readLine();
        System.out.println("Enter last Index: ");
        String last = reader.readLine();
        Integer firstIndex = Integer.parseInt(first);
        Integer lastIndex = Integer.parseInt(last);
        System.out.println("Output: " + reverse(src, firstIndex, lastIndex));
    }

    public static String getSimpleReverse(String simpleString, String simpleReverse) {
        if (simpleString.length() > 0) {
            String reversedWord = "";
            for (int i = simpleString.length() - 1; i >= 0; i--) {
                reversedWord += simpleString.charAt(i);
            }
            simpleReverse += reversedWord;
        }
        return simpleReverse;
    }

    public static String reverse(String src) {
        String simpleString = "";
        String simpleReverse = "";
        for (int i = 0; i < src.length(); i++) {
            if (Character.isAlphabetic(src.charAt(i))) {
                simpleString += src.charAt(i);
            } else {
                simpleReverse = getSimpleReverse(simpleString, simpleReverse);
                simpleString = "";
                simpleReverse += src.charAt(i);
            }
        }
        simpleReverse = getSimpleReverse(simpleString, simpleReverse);
        return simpleReverse;
    }

    public static String reverse(String src, String dest) {
        String simpleString = "";
        if (src.contains(dest)) {
            int indexOfDest = src.indexOf(dest);
            for (int i = 0; i < indexOfDest; i++) {
                simpleString += src.charAt(i);
            }
            simpleString += reverse(dest);
            for (int i = indexOfDest + dest.length(); i < src.length(); i++) {
                simpleString += src.charAt(i);
            }
        } else {
            return src;
        }
        return simpleString;
    }

    public static String reverse(String src, int firstIndex, int lastIndex) {
        if (firstIndex < lastIndex && firstIndex >= 0 && lastIndex < src.length()) {
            String dest = "";
            for (int i = firstIndex; i <= lastIndex; i++) {
                dest += src.charAt(i);
            }
            return reverse(src, dest);
        } else {
            return src;
        }
    }
}
