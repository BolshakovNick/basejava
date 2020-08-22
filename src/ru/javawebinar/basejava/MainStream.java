package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9,8}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4))); //expected = [1, 3]
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5))); //expected = [2, 4]
    }

    public static int minValue(int[] values) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(values).distinct().sorted().forEach(sb::append);
        return Integer.parseInt(sb.toString());
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().mapToInt(x -> x).sum() % 2 == 0 ? integers.stream().filter(integer -> integer % 2 != 0).collect(Collectors.toList()) : integers.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
    }
}