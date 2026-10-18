package ru.otus.danilchenko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Arrays;

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
    void swapFirstOutOfBoundsExceptionCheck() {
        Integer[] expected = {1, 2, 3};
        Integer[] arr = {1, 2, 3};
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Array.swap(arr, -1, 2);
        });
        Assertions.assertArrayEquals(expected, arr);
    }

    @Test
    void swapSecondOutOfBoundsExceptionsCheck() {
        Integer[] expected = {1, 2, 3};
        Integer[] arr = {1, 2, 3};
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Array.swap(arr, 0, -2);
        });
        Assertions.assertArrayEquals(expected, arr);
    }


    @Test
    void swapNullPointerExceptionCheck() {
        Assertions.assertThrows(NullPointerException.class, ()->{
           Array.swap(null, 1, 2);
        });
    }

    @Test
    void toArrayListConversionCheck() {
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,2,3));
        Integer[] arr = {1,2,3};
        var result = Array.toArrayList(arr);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void toArrayListNullPointerCheck() {
        Assertions.assertThrows(NullPointerException.class, ()->{
            Array.toArrayList(null);
        });
    }


}
