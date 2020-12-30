package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
public class NonCaptureLambdaBenchmark {

    @Benchmark
    public static Supplier<String> lambda() {
        return () -> "42";
    }

    @Benchmark
    public static Supplier<String> anonymous() {
        return new Supplier<String>() {
            @Override
            public String get() {
                return "42";
            }
        };
    }

    @Benchmark
    public static Supplier<String> baseline() {
        return null;
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @OperationsPerInvocation(100)
    public Supplier<Supplier> chain_lambda() {
        Supplier<Supplier> top = null;
        for (int i = 0; i < 100; i++) {
            Supplier<Supplier> current = top;
            top = () -> current;
        }
        return top;
    }
}
