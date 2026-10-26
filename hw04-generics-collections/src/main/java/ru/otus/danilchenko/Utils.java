package ru.otus.danilchenko;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;


public class Utils {
    public static List<String> unic(String[] words){
        Objects.requireNonNull(words);
        final var result = new HashSet<String>(words.length);
        for(int i = 0; i < words.length; i++){
            result.add(words[i]);
        }
        return result.stream().toList();
    }
}
