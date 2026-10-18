package ru.otus.danilchenko;

import com.google.common.collect.Lists;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println(String.format("result: %s", Lists.reverse(List.of(1, 2, 3, 4))));
    }
}
