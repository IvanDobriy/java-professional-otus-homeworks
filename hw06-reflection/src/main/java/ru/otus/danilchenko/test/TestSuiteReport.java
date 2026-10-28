package ru.otus.danilchenko.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestSuiteReport {
    private static class TestMethodReport {
        LocalDateTime time;
        long period;
        boolean successfully;
        String msg;

        @Override
        public String toString() {
            return "TestMethodReport{" +
                    "time=" + time +
                    ", period=" + period +
                    ", successfully=" + successfully +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
    private String testSuiteName;
    private Map<Method, TestMethodReport> reportData;
    private LocalDateTime time;
    private long period;
    private boolean successfully;

    public TestSuiteReport(String testSuiteName) {
        this.testSuiteName = testSuiteName;
        reportData = new HashMap<>();
        time = LocalDateTime.now();
        period = 0;
        successfully = true;
    }

    public interface TestExecutor {
        void runTest(Method method, TestBody testBody);
    }

    public interface TestBody {
        void run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    }

    public interface TestSuite {
        void run(TestExecutor testExecutor) throws ClassNotFoundException;
    }

    private class SomeTestExecutor implements TestExecutor {
        @Override
        public void runTest(Method method, TestBody testBody) {
            TestMethodReport tmr = new TestMethodReport();
            tmr.time = LocalDateTime.now();
            tmr.msg = "Ok";
            tmr.successfully = true;
            long testStartTime = System.currentTimeMillis();
            reportData.put(method, tmr);
            try {
                testBody.run();
            } catch (Exception e) {
                successfully = false;
                tmr.successfully = false;
                tmr.msg = e.getMessage() + Arrays.asList(e.getStackTrace());
            } finally {
                tmr.period = System.currentTimeMillis() - testStartTime;
            }
        }
    }

    void start(TestSuite testSuite) {
        time = LocalDateTime.now();
        final long startTimeMills = System.currentTimeMillis();
        try {
            testSuite.run(new SomeTestExecutor());
        } catch (Exception e) {
            successfully = false;
        } finally {
            period = System.currentTimeMillis() - startTimeMills;
        }
    }

    String prepareReport() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("\n\nTest report: %s\n", testSuiteName))
                .append(String.format("time: %s\n", time))
                .append(String.format("duration[ms]: %d\n", period))
                .append(String.format("successfully: %b\n", successfully));
        if(reportData.isEmpty()){
            return builder.toString();
        }
        builder.append("\nTest cases:\n");
        reportData.forEach((method, report)->{
            builder.append(String.format("\ntest name: %s\n", method.getName()))
                    .append(String.format("time: %s\n", report.time))
                    .append(String.format("duration[ms]: %d\n", report.period))
                    .append(String.format("successfully: %b\n", report.successfully))
                    .append(String.format("msg: %s\n", report.msg ));
        });
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportData=" + reportData +
                ", time=" + time +
                ", period=" + period +
                ", successfully=" + successfully +
                '}';
    }
}
