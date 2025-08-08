package com.yogesh.random;

import java.util.ArrayList;
import java.util.List;

public class SplitList {

    public static void splitList(List<Integer> list) {
        int size = list.size();
        int halfSize = size / 2;

        List<Integer> first = new ArrayList<>(list.subList(0, halfSize));
        List<Integer> second = new ArrayList<>(list.subList(halfSize, size));

        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }

    public static void main(String[] args) {
    	ArrayList<Integer> list = new ArrayList<>();
    	list.add(1);
    	list.add(2);
    	list.add(3);
    	list.add(4);
    	list.add(5);
    	splitList(list);

    }
}
