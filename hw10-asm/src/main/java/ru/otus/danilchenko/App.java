package ru.otus.danilchenko;

import ru.otus.danilchenko.asm.AsmGenerator;

public class App {

    public static void main(String[] args) {
        final ICalculator calculator = new Calculator();
        calculator.calculation(1);
        calculator.calculation(1, 2);
        calculator.calculation(1, 2, "hi");


        final var proxy = AsmGenerator.generateProxy(ICalculator.class).apply(calculator);
        proxy.calculation(1);
        proxy.calculation(1, 2);
        proxy.calculation(1, 2, "hi");

    }

}
