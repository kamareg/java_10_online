package ua.com.alevel;

import java.io.*;
import java.util.*;


public class Main {
    private static final File input = new File("input.txt");
    private static final File output = new File("output.txt");
    private static ArrayList<City> cities = new ArrayList<>();
    private static HashMap<Integer, ArrayList<City>> cityPairs = new HashMap<>();
    private static StringBuilder text = new StringBuilder();
    private static StringBuilder outputData = new StringBuilder();
    private static int cityAmount;
    private static int distanceToFound;

    public static void main(String[] args) {
        readFromFile();
        parser();
        if (checkCitiesAmount() && checkDistances() && checkCityPairsAmount() && text.isEmpty()) {
            for (int i = 0; i < cityPairs.size(); i++) {
                outputData.append(findShortestPath(cityPairs.get(i).get(0), cityPairs.get(i).get(1))).append("\n");
            }
        } else {
            System.out.println("The calculation can't be done because the data is not correct");
        }
        writeInFile();
    }

    private static void parser() {
        if (!text.isEmpty()) {
            cityAmountParser();
            citiesParser();
            parseCitiesToFindDistance();
        } else {
            System.out.println("Input file is empty or doesn't exist");
        }
    }

    private static void cityAmountParser() {
        try {
            StringBuilder count = new StringBuilder();
            while (Character.isDigit(text.charAt(0))) {
                charAddDelete(count, text);
            }
            cityAmount = Integer.parseInt(String.valueOf(count));
            text.deleteCharAt(0);
        } catch (NumberFormatException e) {
            System.out.println("Wrong input of cities amount");
        }
    }

    private static void citiesParser() {
        for (int i = 0; i < cityAmount; i++) {
            StringBuilder cityName = new StringBuilder();
            StringBuilder numbers = new StringBuilder();
            extractCityName(cityName);
            if (!text.isEmpty() && text.charAt(0) == '\n') {
                text.deleteCharAt(0);
            }
            extractNumbers(numbers);
            if (!cityName.isEmpty() && !numbers.isEmpty()) {
                createCity(i, cityName, numbers);
                if (!numbers.isEmpty()) {
                    parseDistanceToFound(numbers);
                }
            } else {
                System.out.println("Something wrong with your data");
                cities.clear();
                break;
            }
        }
    }

    private static void parseCitiesToFindDistance() {
        if (!cities.isEmpty()) {
            for (int i = 0; i < distanceToFound; i++) {
                StringBuilder firstCityName = new StringBuilder();
                extractCityName(firstCityName);
                deleteSpaceChar(text);
                StringBuilder secondCityName = new StringBuilder();
                extractCityName(secondCityName);
                deleteSpaceChar(text);
                Optional<City> first = cities.stream().filter(c -> c.getName().contentEquals(firstCityName)).findFirst();
                Optional<City> second = cities.stream().filter(c -> c.getName().contentEquals(secondCityName)).findFirst();
                if (first.isPresent() && second.isPresent()) {
                    ArrayList<City> pair = new ArrayList<>();
                    pair.add(first.get());
                    pair.add(second.get());
                    cityPairs.put(i, pair);
                }
            }
        }
    }

    private static void parseDistanceToFound(StringBuilder numbers) {
        StringBuilder distance = new StringBuilder();
        extractDigits(numbers, distance);
        if (distanceToFound == 0 && !distance.isEmpty()) {
            distanceToFound = Integer.parseInt(String.valueOf(distance));
        } else {
            System.out.println("The number of search paths is duplicated or empty");
            cities.clear();
        }
    }

    private static void extractCityName(StringBuilder cityName) {
        while (!text.isEmpty() &&
                (Character.isLetter(text.charAt(0)) ||
                        (text.charAt(0) == '-') ||
                        (text.charAt(0) == '\''))) {
            charAddDelete(cityName, text);
        }
    }

    private static void extractNumbers(StringBuilder numbers) {
        while (!text.isEmpty() &&
                (Character.isDigit(text.charAt(0)) ||
                        Character.isSpaceChar(text.charAt(0)) ||
                        text.charAt(0) == '\n')) {
            charAddDelete(numbers, text);
        }
    }

    private static void createCity(int i, StringBuilder cityName, StringBuilder numbers) {
        City city = new City();
        city.setIndex(i + 1);
        city.setName(String.valueOf(cityName));
        city.setDistances(new HashMap<>());
        distanceParser(numbers, city);
        cities.add(city);
    }


    private static void charAddDelete(StringBuilder to, StringBuilder from) {
        to.append(from.charAt(0));
        from.deleteCharAt(0);
    }

    private static void distanceParser(StringBuilder numbers, City city) {
        int spaceCount = getSpaceCount(numbers);
        StringBuilder relations = new StringBuilder();
        extractDigits(numbers, relations);
        if (String.valueOf(spaceCount).contentEquals(relations)) {
            numbers.deleteCharAt(0);
            for (int i = 0; i < spaceCount; i++) {
                StringBuilder cityIndex = new StringBuilder();
                StringBuilder distance = new StringBuilder();
                extractDigits(numbers, cityIndex);
                deleteSpaceChar(numbers);
                extractDigits(numbers, distance);
                deleteSpaceChar(numbers);
                if (!cityIndex.isEmpty() && !distance.isEmpty()) {
                    city.getDistances().put(Integer.parseInt(String.valueOf(cityIndex)), Integer.parseInt(String.valueOf(distance)));
                } else {
                    System.out.println("Something wrong with your data");
                    cities.clear();
                    break;
                }
            }
        } else {
            System.out.println("Your distances are incorrect");
            cities.clear();
        }
    }

    private static void deleteSpaceChar(StringBuilder builder) {
        if (!builder.isEmpty() && (Character.isSpaceChar(builder.charAt(0)) || builder.charAt(0) == '\n')){
            builder.deleteCharAt(0);
        }
    }

    private static int getSpaceCount(StringBuilder numbers) {
        int spaceCount = 0;
        for (int i = 0; i < numbers.length(); i++) {
            if (Character.isSpaceChar(numbers.charAt(i))) {
                spaceCount++;
            }
        }
        return spaceCount;
    }

    private static void extractDigits(StringBuilder string, StringBuilder builder) {
        while (!string.isEmpty() &&
                Character.isDigit(string.charAt(0))) {
            charAddDelete(builder, string);
        }
    }


    private static boolean checkCitiesAmount() {
        if ((cities.size() != cityAmount) || cityAmount == 0) {
            System.out.println("Something wrong with your data");
            return false;
        }
        return true;
    }

    private static boolean checkCityPairsAmount() {
        return (distanceToFound == cityPairs.size() && distanceToFound != 0);
    }

    private static boolean checkDistances() {
        for (City city : cities) {
            for (Map.Entry<Integer, Integer> entry : city.getDistances().entrySet()) {
                int neighborIndex = entry.getKey();
                int distance = entry.getValue();
                City neighbor = null;
                for (City c : cities) {
                    if (c.getIndex() == neighborIndex) {
                        neighbor = c;
                        break;
                    }
                }
                if (neighbor != null) {
                    if (neighbor.getDistances().containsKey(city.getIndex())) {
                        if (neighbor.getDistances().get(city.getIndex()) != distance) {
                            System.out.println("Invalid distance between " + city.getName() + " and " + neighbor.getName());
                            return false;
                        }
                    } else {
                        System.out.println("Neighbor doesn't contain " + city.getName() + " index");
                    }
                } else {
                    System.out.println("Invalid neighbor index for city " + city.getName());
                    return false;
                }
            }
        }
        return true;
    }

    private static void readFromFile() {
        try (
                FileReader fileReader = new FileReader(input);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while (bufferedReader.ready()) {
                text.append(bufferedReader.readLine()).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeInFile() {
        try (FileWriter fileWriter = new FileWriter(output)) {
            fileWriter.write(String.valueOf(outputData));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int findShortestPath(City source, City destination) {
        HashMap<City, Integer> distances = new HashMap<>();
        initializeCityWeight(source, distances);
        algorithmDijkstra(destination, source, distances);
        return distances.get(destination);
    }

    private static void algorithmDijkstra(City destination, City source, HashMap<City, Integer> distances) {
        PriorityQueue<City> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        queue.add(source);
        while (!queue.isEmpty()) {
            City currentCity = queue.poll();
            if (currentCity.equals(destination)) {
                break;
            }
            for (Map.Entry<Integer, Integer> neighborEntry : currentCity.getDistances().entrySet()) {
                City neighbor = cities.get(neighborEntry.getKey() - 1);
                int distanceToNeighbor = distances.get(currentCity) + neighborEntry.getValue();
                if (distanceToNeighbor < distances.get(neighbor)) {
                    distances.put(neighbor, distanceToNeighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    private static void initializeCityWeight(City source, HashMap<City, Integer> distances) {
        for (City city : cities) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
    }
}
