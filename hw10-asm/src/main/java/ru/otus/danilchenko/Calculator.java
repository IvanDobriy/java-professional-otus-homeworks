package ru.otus.danilchenko;

import java.io.PrintStream;

public class Calculator implements ICalculator {
    private final static PrintStream out = System.out;

    @Override
    public void calculation(int param1) {
        out.println(String.format("calculator, param: %s", param1));
    }

    @Override
    public void calculation(int param1, int param2) {
        out.println(String.format("calculator, param1: %s, param2: %s", param1, param2));
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        out.println(String.format("calculator, param1: %s, param2: %s, param3: %s", param1, param2, param3));
    }
}
