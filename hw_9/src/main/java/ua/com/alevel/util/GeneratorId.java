package ua.com.alevel.util;

import java.util.ArrayList;
import java.util.UUID;

public final class GeneratorId {
    private static final ArrayList<String> ids = new ArrayList<>();

    private GeneratorId() {
    }

    public static String generateId() {
        String id = UUID.randomUUID().toString();
        if (ids.stream().anyMatch(x -> x.equals(id))) {
            return generateId();
        } else {
            ids.add(id);
            return id;
        }
    }

    public static void idSaving(ArrayList<String> idList) {
        ids.addAll(idList);
    }
}
