package ru.otus.danilchenko;

import ru.otus.danilchenko.test.After;
import ru.otus.danilchenko.test.Before;
import ru.otus.danilchenko.test.Test;

public class SomeTest {

    private int counter = 0;

    @Before
    private void before1() {
        counter++;
        System.out.println("before1, counter: " + counter);
    }

    @Before
    private void before2() {
        counter++;
        System.out.println("before2, counter: " + counter);
    }

    @After
    private void after1() {
        counter--;
        System.out.println("after1, counter: " + counter);
    }

    @After
    private void after2() {
        counter--;
        System.out.println("after2, counter: " + counter);
    }

    @Test
    private void test1() {
        System.out.println("test1");
    }

    @Test
    private void test2() {
        System.out.println("test2");
    }

    @Test
    private void test3() {
        System.out.println("test3");
//        throw new RuntimeException("bla bla bla");
    }
}
