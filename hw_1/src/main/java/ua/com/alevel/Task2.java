package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task2 {
    public static void main(String[] args) throws IOException {
        String result = getString();
        char[] sortedChars = result.toCharArray();
        sort(sortedChars);
        output(sortedChars);
    }

    public static void output(char[] sortedChars) {
        int count = 1;
        int sum = 1;
        if (sortedChars.length > 0) {
            if (sortedChars.length == 1) {
                print(sortedChars[0], count, sum);
            } else {
                for (int i = 0; i < sortedChars.length - 1; i++) {
                    if (sortedChars[i] == sortedChars[i + 1]) {
                        sum++;
                    } else {
                        print(sortedChars[i], count, sum);
                        count++;
                        sum = 1;
                    }
                }
                if (sum > 1) {
                    print(sortedChars[sortedChars.length - 1], count, sum);
                } else {
                    print(sortedChars[sortedChars.length - 1], count, 1);
                }
            }
        } else {
            System.out.println("There are no letters");
        }
    }

    public static void print(char ch, int count, int sum) {
        System.out.println(count + ". " + ch + " - " + sum);
    }

    public static void sort(char[] sortedChars) {
        for (int i = 0; i < sortedChars.length; i++) {
            for (int j = i + 1; j < sortedChars.length; j++) {
                if (sortedChars[i] > sortedChars[j]) {
                    char temp = sortedChars[i];
                    sortedChars[i] = sortedChars[j];
                    sortedChars[j] = temp;
                }
            }
        }
    }

    public static String getString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the string: ");
        String s = reader.readLine();
        char[] chars = s.toCharArray();
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= 'a' && chars[i] <= 'z')
                    || (chars[i] >= 'A' && chars[i] <= 'Z')
                    || (chars[i] >= 'à' && chars[i] <= 'ÿ')
                    || (chars[i] >= 'À' && chars[i] <= 'ß')) {
                result += chars[i];
            }
        }
        return result;
    }
}
