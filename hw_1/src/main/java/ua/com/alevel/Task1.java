package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the string: ");
        String s = reader.readLine();
        int sum = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                sum += Integer.parseInt(String.valueOf(chars[i]));
            }
        }
        System.out.println("Here is your result: " + sum);
    }
}
