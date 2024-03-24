package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, batchSize = 10000)
@Measurement(iterations = 10, time = 1, batchSize = 10000)
public class ShutdownSuccessVsShutdownOnFailure {

    @Benchmark
    public String onSuccess() {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            StructuredTaskScope.Subtask<String> task1 = scope.fork(Weather::getTempFromA);
            StructuredTaskScope.Subtask<String> task2 = scope.fork(Weather::getTempFromB);
            StructuredTaskScope.Subtask<String> task3 = scope.fork(Weather::getTempFromC);
            scope.join();
            return scope.result();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public void onFailure() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            StructuredTaskScope.Subtask<String> task1 = scope.fork(Weather::getTempFromA);
            StructuredTaskScope.Subtask<String> task2 = scope.fork(Weather::getTempFromB);
            StructuredTaskScope.Subtask<String> task3 = scope.fork(Weather::getTempFromC);
            scope.join();
            scope.throwIfFailed(RuntimeException::new);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class Weather {
        static Random random = new Random();

        public static String getTempFromA() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return STR."Temp from A: Temp = \{random.nextInt(0, 100)}";
        }

        public static String getTempFromB() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return STR."Temp from B: Temp = \{random.nextInt(0, 100)}";
        }

        public static String getTempFromC() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return STR."Temp from C: Temp = \{random.nextInt(0, 100)}";
        }
    }
}