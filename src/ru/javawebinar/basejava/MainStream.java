package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9,8}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4))); //expected = [1, 3]
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5))); //expected = [2, 4]
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, b) -> (int) Math.pow(10, (int) (Math.log10(b) + 1)) * a + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        IntStream stream = integers.stream().mapToInt(x -> x);
        return integers.stream().filter(stream.sum() % 2 == 0 ? x -> x % 2 != 0 : x -> x % 2 == 0).collect(Collectors.toList());
    }
}