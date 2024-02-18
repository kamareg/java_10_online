package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please enter text!");
            String text = reader.readLine();
            System.out.printf("%-10s %-10s %-10s %-10s \n", "", "Rating", "Count", "Percentage");
            processing(text);
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    private static void processing(String text) {
        List<String> wordsList = Arrays.stream(text.split(" "))
                .map(w -> w.replaceAll("[^À-ÿà-ÿa-zA-Z0-9]", ""))
                .filter(word -> !word.isEmpty())
                .toList();

        int countAllWords = wordsList.size();

        Map<String, Long> wordsMap = wordsList.stream()
                .collect(groupingBy(String::toLowerCase, counting()));

        List<Word> wordsAsObjects = wordsMap.entrySet().stream()
                .map(entry -> new Word(entry.getKey(), entry.getValue(), entry.getValue() * 100 / countAllWords))
                .sorted(Comparator.comparing(Word::getPercentage).reversed())
                .toList();

        wordsAsObjects.stream().peek(w -> w.setRating((long) (wordsAsObjects.indexOf(w) + 1)))
                .forEach(word -> System.out.printf("%-13s %-10s %-10s %-10s \n", word.getWord(), word.getRating(), word.getCount(), word.getPercentage()));
    }
}
