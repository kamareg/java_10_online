package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number: ");
        String s = reader.readLine();
        int num = Integer.parseInt(s);

        int count = (num + 1) / 2;
        int lessons = num * 50;
        int breaks = count * 10;
        int total = lessons + breaks - 15;

        int hours = total / 60;
        int min = total - (hours * 60);

        System.out.println(9 + hours + " " + min);
    }
}
