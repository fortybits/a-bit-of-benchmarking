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
@Warmup(iterations = 20, time = 1, batchSize = 10000)
@Measurement(iterations = 20, time = 1, batchSize = 10000)
public class CollectorsVsStreamToList {

    @Benchmark
    public List<Integer> viaCollectors() {
        return IntStream.range(1, 1000).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> viaStream() {
        return IntStream.range(1, 1000).boxed().toList();
    }

    /*
     * Benchmark                                                       Mode  Cnt   Score    Error  Units
     * CollectorsVsStreamToList.viaCollectors                         thrpt   20  17.321 ±  0.583  ops/s
     * CollectorsVsStreamToList.viaStream                             thrpt   20  23.879 ±  1.682  ops/s
     * CollectorsVsStreamToList.viaCollectors                          avgt   20   0.057 ±  0.002   s/op
     * CollectorsVsStreamToList.viaStream                              avgt   20   0.040 ±  0.001   s/op
     * CollectorsVsStreamToList.viaCollectors                        sample  380   0.054 ±  0.001   s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.00    sample        0.051            s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.50    sample        0.054            s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.90    sample        0.058            s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.95    sample        0.058            s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.99    sample        0.062            s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.999   sample        0.068            s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.9999  sample        0.068            s/op
     * CollectorsVsStreamToList.viaCollectors:viaCollectors·p1.00    sample        0.068            s/op
     * CollectorsVsStreamToList.viaStream                            sample  525   0.039 ±  0.001   s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p0.00            sample        0.037            s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p0.50            sample        0.038            s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p0.90            sample        0.040            s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p0.95            sample        0.042            s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p0.99            sample        0.050            s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p0.999           sample        0.051            s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p0.9999          sample        0.051            s/op
     * CollectorsVsStreamToList.viaStream:viaStream·p1.00            sample        0.051            s/op
     * CollectorsVsStreamToList.viaCollectors                            ss   20   0.060 ±  0.007   s/op
     * CollectorsVsStreamToList.viaStream                                ss   20   0.043 ±  0.006   s/op
     */
}