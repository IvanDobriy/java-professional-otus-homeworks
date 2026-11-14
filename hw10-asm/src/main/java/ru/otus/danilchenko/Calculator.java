package ru.otus.danilchenko;

import ru.otus.danilchenko.asm.Log;

import java.io.PrintStream;

public class Calculator implements ICalculator {
    @Log
    @Override
    public void calculation(int param1) {
        System.out.println(String.format("calculator, param: %s", param1));
    }
    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println(String.format("calculator, param1: %s, param2: %s", param1, param2));
    }
    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println(String.format("calculator, param1: %s, param2: %s, param3: %s", param1, param2, param3));
    }
}
