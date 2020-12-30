package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
public class StringEmptyVsEquals {

    @Param({"", "nonEmptyString"})
    private String strParams;

    @Benchmark
    public boolean nonNullAndIsEmpty() {
        return strParams != null && strParams.isEmpty();
    }

    @Benchmark
    public boolean equalsPost() {
        return strParams != null && strParams.equals("");
    }

    @Benchmark
    public boolean preEquals() {
        return "".equals(strParams);
    }
}