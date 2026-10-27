package ru.otus.danilchenko;

import ru.otus.danilchenko.test.TestRunner;

public class App {
    void main(String[] args) {
        TestRunner.run(SomeTest.class.getName());
    }
}
