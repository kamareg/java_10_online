package ua.com.alevel;

import java.util.HashMap;

public class City {
    private String name;
    private int index;
    private HashMap<Integer, Integer> distances;

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public HashMap<Integer, Integer> getDistances() {
        return distances;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDistances(HashMap<Integer, Integer> distances) {
        this.distances = distances;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", distances=" + distances +
                '}';
    }
}
