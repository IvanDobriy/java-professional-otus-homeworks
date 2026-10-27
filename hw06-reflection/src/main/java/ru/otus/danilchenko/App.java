package ru.otus.danilchenko;

import ru.otus.danilchenko.test.TestRunner;

public class App {
    void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        testRunner.run(SomeTest.class.getName());
    }
}
