package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
public class EqualIgnoreCaseVsToLowerCase {

    @Param({"HELLO WORLD", "Hello World", "hello world", "otherParam"})
    String strParams;

    @Benchmark
    public boolean equalsAndToLowerCase() {
        return "hello world".equals(strParams.toLowerCase());
    }

    @Benchmark
    public boolean equalsIgnoreCase() {
        return "hello world".equalsIgnoreCase(strParams);
    }

    @Benchmark
    public boolean equals() {
        return "hello world".equals(strParams);
    }
}