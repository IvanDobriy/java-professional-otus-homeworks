package ru.otus.danilchenko.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class TestRunner {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private interface Callback {
        void run() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    }

    private void withErrorHandling(Callback callback) {
        Objects.requireNonNull(callback);
        try {
            callback.run();
        } catch (Exception e) {
            logger.severe(String.format("Msg: %s, stackTrace: %s", e.getMessage(), Arrays.asList(e.getStackTrace())));
        }
    }


    private static boolean ensureAccessable(Method method, Object object) {
        return method.canAccess(object) || method.trySetAccessible();
    }


    public void run(String className) {
        Objects.requireNonNull(className);
        withErrorHandling(() -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
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
                try {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    Object testObject = constructor.newInstance();
                    for (Method beforeMethod : beforeMethods) {
                        if (ensureAccessable(beforeMethod, testObject)) {
                            beforeMethod.invoke(testObject);
                        }
                    }

                    if (ensureAccessable(testMethod, testObject)) {
                        testMethod.invoke(testObject);
                    } else {
                        //todo handle can`t invoke logic
                    }

                    for (Method afterMethod : afterMethods) {
                        if (ensureAccessable(afterMethod, testObject)) {
                            afterMethod.invoke(testObject);
                        }
                    }
                } catch (Exception e) {
                    logger.severe(e.getCause().getMessage());
                }
            }
        });

    }
}
