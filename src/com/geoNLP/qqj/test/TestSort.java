package com.geoNLP.qqj.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestSort {

	public static void main(String[] args) {

		Map<String, Double> maps = new HashMap<String, Double>();
		maps.put("a", new Double(1));
		maps.put("b", new Double(2));
		ByValueComparator bvc = new ByValueComparator(maps);
		ArrayList<String> keys = new ArrayList<String>(maps.keySet());
		Collections.sort(keys, bvc);
		
		System.out.println(keys);
		
	}

}
