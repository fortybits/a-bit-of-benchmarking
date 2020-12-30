package jmh;


import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
public class StringEqualsOneCharacter {

    @Param({"/", "/my/server/url"})
    private String url;

    private static boolean equalsOneChar(String url) {
        return url != null && url.length() == 1 && url.charAt(0) == '/';
    }

    @Benchmark
    public boolean equalsPost() {
        return url != null && url.equals("/");
    }

    @Benchmark
    public boolean equalsPre() {
        return "/".equals(url);
    }

    @Benchmark
    public boolean equalsOptimized() {
        return equalsOneChar(url);
    }
}