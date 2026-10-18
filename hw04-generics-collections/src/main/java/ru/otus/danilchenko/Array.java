package ru.otus.danilchenko;

import java.util.Objects;

public class Array {
    public static <T> void swap(T[] arr, int first, int second) {
        Objects.requireNonNull(arr);
        T tmp = arr[first];
        arr[first] = arr[second];
        arr[second] = tmp;
    }
}
