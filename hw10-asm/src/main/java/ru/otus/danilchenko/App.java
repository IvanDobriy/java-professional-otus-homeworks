package ru.otus.danilchenko;

public class App {

    public static void main(String[] args) {
        ICalculator calculator = new Calculator();
        calculator.calculation(1);
        calculator.calculation(1, 2);
        calculator.calculation(1, 2, "hi");
    }

}
