package fr.pantheonsorbonne.cri;

import java.util.ArrayList;
import java.util.List;

public class IdGenerator {
    private static final List<Integer> IDS = generateIds();

    public static List<Integer> generateIds() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i <= 999999999; i++) {
            ids.add(i);
        }
        return ids;
    }

    public List<Integer> getIds() {
        return IDS;
    }
}
