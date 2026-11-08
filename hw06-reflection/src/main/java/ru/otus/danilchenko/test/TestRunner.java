package ru.otus.danilchenko.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestRunner {

    private static void tryInvoke(Method method, Object object) throws InvocationTargetException, IllegalAccessException {
        if (method.canAccess(object) || method.trySetAccessible()) {
            method.invoke(object);
        } else {
            throw new RuntimeException(String.format("Can`t invoke annotated method: %s", method.getName()));
        }
    }


    public static void run(String className) {
        Objects.requireNonNull(className);
        TestSuiteReport testSuiteReport = new TestSuiteReport(className);
        testSuiteReport.start((testExecutor) -> {
            ClassLoader classLoader = TestRunner.class.getClassLoader();
            Class<?> clazz = classLoader.loadClass(className);

            final List<Method> testMethods = new ArrayList<>();
            final List<Method> beforeMethods = new ArrayList<>();
            final List<Method> afterMethods = new ArrayList<>();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                }
                if (method.isAnnotationPresent(Before.class)) {
                    beforeMethods.add(method);
                }
                if (method.isAnnotationPresent(After.class)) {
                    afterMethods.add(method);
                }
            }
            for (Method testMethod : testMethods) {
                testExecutor.runTest(testMethod, () -> {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    Object testObject = constructor.newInstance();
                    for (Method beforeMethod : beforeMethods) {
                        tryInvoke(beforeMethod, testObject);
                    }
                    tryInvoke(testMethod, testObject);
                    for (Method afterMethod : afterMethods) {
                        tryInvoke(afterMethod, testObject);
                    }
                });
            }
        });
        System.out.println(testSuiteReport.prepareReport());
    }
}
