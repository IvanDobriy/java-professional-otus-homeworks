package ru.otus.danilchenko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArrayTest {
    @Test
    void swapTest() {
        Integer[] expected = {3, 2, 1};
        Integer[] arr = {1, 2, 3};
        Array.swap(arr, 0, 2);
        Assertions.assertArrayEquals(expected, arr);
    }

    @Test
    void firstOutOfBoundsExceptionCheck() {
        Integer[] expected = {1, 2, 3};
        Integer[] arr = {1, 2, 3};
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Array.swap(arr, -1, 2);
        });
        Assertions.assertArrayEquals(expected, arr);
    }

    @Test
    void secondOutOfBoundsExceptionsCheck() {
        Integer[] expected = {1, 2, 3};
        Integer[] arr = {1, 2, 3};
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Array.swap(arr, 0, -2);
        });
        Assertions.assertArrayEquals(expected, arr);
    }
}
