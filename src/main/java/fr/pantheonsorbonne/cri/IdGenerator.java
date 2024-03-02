package fr.pantheonsorbonne.cri;

import java.util.ArrayList;
import java.util.List;

public class IdGenerator {
    private static final List<Long> IDS = generateIds();

    public static List<Long> generateIds() {
        List<Long> ids = new ArrayList<>();
        for (long i = 0; i <= 999999999999L; i++) {
            ids.add(i);
        }
        return ids;
    }

    public List<Long> getIds() {
        return IDS;
    }
}
