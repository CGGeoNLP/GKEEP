package com.geoNLP.qqj.test;
import java.util.Comparator;
import java.util.Map;

public class ByValueComparator implements Comparator<String> {
    Map<String, Double> base_map;

    public ByValueComparator(Map<String, Double> base_map) {
        this.base_map = base_map;
    }

    public int compare(String arg0, String arg1) {
        if (!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
            return 0;
        }

        if (base_map.get(arg0) < base_map.get(arg1)) {
            return 1;
        } else if (base_map.get(arg0) > base_map.get(arg1)) {
            return -1;
        } else {
            return 0;
        }
    }
}