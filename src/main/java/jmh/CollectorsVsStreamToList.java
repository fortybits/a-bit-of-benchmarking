package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * feature introduced with https://bugs.openjdk.java.net/browse/JDK-8180352
 */
@BenchmarkMode(Mode.All)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, batchSize = 1000)
@Measurement(iterations = 10, time = 1, batchSize = 1000)
public class CollectorsVsStreamToList {

    @Benchmark
    public List<Integer> viaCollectors() {
        return IntStream.range(1, 1000).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> viaStream() {
        return IntStream.range(1, 1000).boxed().toList();
    }
}