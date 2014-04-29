package fr.cea.labgem.obo;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestParser.class, TestVariant.class);
        for (Failure failure : result.getFailures()) {
            System.out.println("[NO] " + failure.getTestHeader());
            System.out.println("Test result " + failure.getMessage() + "\n");
        }
        System.out.println( String.format( "Run: %d | Failled: %d | Ignored: %d | Ellapsed time: %d ms", result.getRunCount(), result.getFailureCount(), result.getIgnoreCount(), result.getRunTime() )  );
    }
}
