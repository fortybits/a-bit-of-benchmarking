package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Set;
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

    static final Set<Integer> data = IntStream.range(0, 1000)
            .boxed()
            .collect(Collectors.toSet());

    @Benchmark
    public List<Integer> viaCollectors() {
        return data.stream().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> viaStream() {
        return data.stream().toList();
    }

    @Benchmark
    public List<Integer> viaCollectorsParallel() {
        return data.stream().parallel().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> viaStreamParallel() {
        return data.stream().parallel().toList();
    }
}
/*
 * Benchmark                                                                       Mode  Cnt   Score    Error  Units
 * CollectorsVsStreamToList.viaCollectors                                         thrpt   20   6.978 ±  0.012  ops/s
 * CollectorsVsStreamToList.viaStream                                             thrpt   20  18.648 ±  0.632  ops/s
 * CollectorsVsStreamToList.viaCollectorsParallel                                 thrpt   20   2.999 ±  0.003  ops/s
 * CollectorsVsStreamToList.viaStreamParallel                                     thrpt   20   1.000 ±  0.001  ops/s
 * <p>
 * CollectorsVsStreamToList.viaCollectors                                          avgt   20   0.143 ±  0.001   s/op
 * CollectorsVsStreamToList.viaStream                                              avgt   20   0.072 ±  0.001   s/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                  avgt   20   0.333 ±  0.001   s/op
 * CollectorsVsStreamToList.viaStreamParallel                                      avgt   20   0.953 ±  0.134   s/op
 * <p>
 * CollectorsVsStreamToList.viaCollectors                                        sample  160   0.132 ±  0.001   s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.00                    sample        0.127            s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.50                    sample        0.132            s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.90                    sample        0.135            s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.95                    sample        0.137            s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.99                    sample        0.148            s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.999                   sample        0.153            s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.9999                  sample        0.153            s/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p1.00                    sample        0.153            s/op
 * CollectorsVsStreamToList.viaStream                                            sample  397   0.052 ±  0.001   s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.00                            sample        0.050            s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.50                            sample        0.052            s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.90                            sample        0.054            s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.95                            sample        0.055            s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.99                            sample        0.058            s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.999                           sample        0.069            s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.9999                          sample        0.069            s/op
 * CollectorsVsStreamToList.viaStream:viaStream·p1.00                            sample        0.069            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                sample   80   0.296 ±  0.006   s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.00    sample        0.257            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.50    sample        0.297            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.90    sample        0.308            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.95    sample        0.313            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.99    sample        0.363            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.999   sample        0.363            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.9999  sample        0.363            s/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p1.00    sample        0.363            s/op
 * CollectorsVsStreamToList.viaStreamParallel                                    sample   41   0.549 ±  0.029   s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.00            sample        0.478            s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.50            sample        0.533            s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.90            sample        0.606            s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.95            sample        0.657            s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.99            sample        0.757            s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.999           sample        0.757            s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.9999          sample        0.757            s/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p1.00            sample        0.757            s/op
 * <p>
 * CollectorsVsStreamToList.viaCollectors                                            ss   20   0.135 ±  0.002   s/op
 * CollectorsVsStreamToList.viaStream                                                ss   20   0.055 ±  0.003   s/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                    ss   20   0.294 ±  0.012   s/op
 * CollectorsVsStreamToList.viaStreamParallel                                        ss   20   0.576 ±  0.078   s/op
 */