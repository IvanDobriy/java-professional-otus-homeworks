package ru.otus.danilchenko.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestRunner {

    private static boolean ensureAccessable(Method method, Object object) {
        return method.canAccess(object) || method.trySetAccessible();
    }

    public static void run(String className) {
        Objects.requireNonNull(className);
        Report report = new Report();
        report.start((someCallback) -> {
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
                someCallback.runTest(testMethod, () -> {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    Object testObject = constructor.newInstance();
                    for (Method beforeMethod : beforeMethods) {
                        if (ensureAccessable(beforeMethod, testObject)) {
                            beforeMethod.invoke(testObject);
                        } else {
                            throw new RuntimeException(String.format("Can`t invoke method: %s", beforeMethod.getName()));
                        }
                    }
                    if (ensureAccessable(testMethod, testObject)) {
                        testMethod.invoke(testObject);
                    } else {
                        throw new RuntimeException(String.format("Can`t invoke test method: %s", testMethod.getName()));
                    }
                    for (Method afterMethod : afterMethods) {
                        if (ensureAccessable(afterMethod, testObject)) {
                            afterMethod.invoke(testObject);
                        } else {
                            throw new RuntimeException(String.format("Can`t invoke  method: %s", afterMethod.getName()));
                        }
                    }
                });
            }
        });
    }
}
