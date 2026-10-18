package ru.otus.danilchenko;

import java.util.ArrayList;
import java.util.Objects;

public class Array {
    public static <T> void swap(T[] arr, int first, int second) {
        Objects.requireNonNull(arr);
        T tmp = arr[first];
        arr[first] = arr[second];
        arr[second] = tmp;
    }

    public static <T> ArrayList<T> toArrayList(T[] arr) {
        Objects.requireNonNull(arr);
        final var result = new ArrayList<T>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
