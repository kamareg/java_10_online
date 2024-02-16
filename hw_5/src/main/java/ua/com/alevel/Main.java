package ua.com.alevel;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Dictionary<String, Double> dictionary = new Dictionary<>();
        System.out.println("toString: " + dictionary);
        System.out.println("size before: " + dictionary.size());
        System.out.println("isEmpty before: " + dictionary.isEmpty());
        for (int i = 0; i < 12; i++) {
            dictionary.put(String.valueOf(i), (double) (17 / (i + 1)));
        }
        System.out.println("put the same K,V: " + dictionary.put("1", 8.0));
        System.out.println("put different V: " + dictionary.put("1", 34.9));
        System.out.println("toString after: " + dictionary);
        System.out.println("size after: " + dictionary.size());
        System.out.println("isEmpty after: " + dictionary.isEmpty());
        System.out.println("contains really Key: " + dictionary.containsKey("0"));
        System.out.println("contains a fictitious Key: " + dictionary.containsKey("000"));
        System.out.println("contains really Value: " + dictionary.containsValue(17.0));
        System.out.println("contains a fictitious Value: " + dictionary.containsValue(0.0));
        System.out.println("get really Key: " + dictionary.get("0"));
        System.out.println("get fictitious Key: " + dictionary.get("000"));
        System.out.println("remove really Key: " + dictionary.remove("0"));
        System.out.println("remove fictitious Key: " + dictionary.remove("000"));
        System.out.println("toString after: " + dictionary);
        System.out.println("size after: " + dictionary.size());
        System.out.println("keys(): " + Arrays.toString(dictionary.keys()));
        System.out.println("values(): " + Arrays.toString(dictionary.values()));
        Dictionary<String, Double> dictionary2 = new Dictionary<>();
        for (int i = 0; i < 3; i++) {
            dictionary2.put(String.valueOf(i * 10), (double) (i));
        }
        System.out.println("toString dictionary2: " + dictionary2);
        System.out.println("dictionary.putAll(dictionary2): " + dictionary.putAll(dictionary2));
        System.out.println("toString after: " + dictionary);
        System.out.println("dictionary2.clear(): " + dictionary2.clear());
        System.out.println("toString dictionary2: " + dictionary2);
    }
}