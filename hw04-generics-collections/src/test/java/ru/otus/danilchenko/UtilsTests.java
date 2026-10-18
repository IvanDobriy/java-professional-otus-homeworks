package ru.otus.danilchenko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilsTests {
    @Test
    void unicCheck() {
        final String[] words = {
                "1", "2", "3", "4", "5",
                "1", "2", "3", "4", "5",
                "1", "2", "3", "4", "5",
                "1", "2", "3", "4", "5",
        };
        final var expected = List.of("1", "2", "3", "4", "5");
        final var result = Utils.unic(words);
        Assertions.assertEquals(expected, result);
    }
    @Test
    void unicNullPointerCheck() {
        Assertions.assertThrows(NullPointerException.class, ()->{
           Utils.unic(null);
        });
    }
}
