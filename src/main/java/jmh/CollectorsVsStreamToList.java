package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * feature introduced with https://bugs.openjdk.java.net/browse/JDK-8180352
 */
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 20, time = 1, batchSize = 10000)
@Measurement(iterations = 20, time = 1, batchSize = 10000)
public class CollectorsVsStreamToList {

    @State(Scope.Benchmark)
    public static class MyState {
        Integer[] randomIntsArray = IntStream.generate(() -> new Random().nextInt(1000))
                .boxed()
                .limit(1000)
                .toArray(Integer[]::new);

    }

    @Benchmark
    public List<Integer> viaCollectors(MyState myState) {
        return Arrays.stream(myState.randomIntsArray).collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> viaStream(MyState myState) {
        return Arrays.stream(myState.randomIntsArray).toList();
    }

    @Benchmark
    public List<Integer> viaCollectorsParallel(MyState myState) {
        return Arrays.stream(myState.randomIntsArray).parallel().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> viaStreamParallel(MyState myState) {
        return Arrays.stream(myState.randomIntsArray).parallel().toList();
    }
}
/*
 * Benchmark         (with Set)                                                    Mode  Cnt     Score    Error   Units
 * CollectorsVsStreamToList.viaCollectors                                         thrpt   20     0.010 ±  0.001  ops/ms
 * CollectorsVsStreamToList.viaStream                                             thrpt   20     0.017 ±  0.001  ops/ms
 * CollectorsVsStreamToList.viaCollectorsParallel                                 thrpt   20     0.003 ±  0.001  ops/ms
 * CollectorsVsStreamToList.viaStreamParallel                                     thrpt   20     0.001 ±  0.001  ops/ms
 * <p>
 * CollectorsVsStreamToList.viaCollectors                                          avgt   20   156.064 ± 16.285   ms/op
 * CollectorsVsStreamToList.viaStream                                              avgt   20    56.588 ±  1.635   ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                  avgt   20   451.144 ± 68.336   ms/op
 * CollectorsVsStreamToList.viaStreamParallel                                      avgt   20  1002.167 ±  1.663   ms/op
 * <p>
 * CollectorsVsStreamToList.viaCollectors                                        sample  156   142.052 ±  1.313   ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.00                    sample        138.150            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.50                    sample        141.033            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.90                    sample        143.550            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.95                    sample        150.209            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.99                    sample        172.611            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.999                   sample        175.899            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.9999                  sample        175.899            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p1.00                    sample        175.899            ms/op
 * CollectorsVsStreamToList.viaStream                                            sample  371    55.071 ±  0.644   ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.00                            sample         52.101            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.50                            sample         53.936            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.90                            sample         58.471            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.95                            sample         65.352            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.99                            sample         71.078            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.999                           sample         71.565            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.9999                          sample         71.565            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p1.00                            sample         71.565            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                sample   60   381.227 ± 33.472   ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.00    sample        262.668            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.50    sample        362.545            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.90    sample        480.667            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.95    sample        550.738            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.99    sample        619.708            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.999   sample        619.708            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.9999  sample        619.708            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p1.00    sample        619.708            ms/op
 * CollectorsVsStreamToList.viaStreamParallel                                    sample   40   607.925 ± 24.013   ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.00            sample        518.521            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.50            sample        609.223            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.90            sample        661.127            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.95            sample        687.394            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.99            sample        700.449            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.999           sample        700.449            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.9999          sample        700.449            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p1.00            sample        700.449            ms/op
 * <p>
 * CollectorsVsStreamToList.viaCollectors                                            ss   20   140.328 ±  7.445   ms/op
 * CollectorsVsStreamToList.viaStream                                                ss   20    55.207 ±  3.109   ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                    ss   20   330.194 ± 23.802   ms/op
 * CollectorsVsStreamToList.viaStreamParallel                                        ss   20   596.188 ± 43.737   ms/op
 */
/*
 * Benchmark (with array)                                                          Mode  Cnt    Score    Error   Units
 * CollectorsVsStreamToList.viaCollectors                                         thrpt   20    0.020 ±  0.001  ops/ms
 * CollectorsVsStreamToList.viaStream                                             thrpt   20    0.045 ±  0.002  ops/ms
 * CollectorsVsStreamToList.viaCollectorsParallel                                 thrpt   20    0.003 ±  0.001  ops/ms
 * CollectorsVsStreamToList.viaStreamParallel                                     thrpt   20    0.003 ±  0.001  ops/ms
 *
 * CollectorsVsStreamToList.viaCollectors                                          avgt   20   50.983 ±  2.959   ms/op
 * CollectorsVsStreamToList.viaStream                                              avgt   20   30.332 ±  1.695   ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                  avgt   20  350.238 ± 44.639   ms/op
 * CollectorsVsStreamToList.viaStreamParallel                                      avgt   20  342.445 ± 32.524   ms/op
 *
 * CollectorsVsStreamToList.viaCollectors                                        sample  425   48.241 ±  0.558   ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.00                    sample        44.958            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.50                    sample        47.251            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.90                    sample        52.154            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.95                    sample        56.846            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.99                    sample        61.194            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.999                   sample        66.322            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p0.9999                  sample        66.322            ms/op
 * CollectorsVsStreamToList.viaCollectors:viaCollectors·p1.00                    sample        66.322            ms/op
 * CollectorsVsStreamToList.viaStream                                            sample  698   29.022 ±  0.334   ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.00                            sample        26.378            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.50                            sample        28.049            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.90                            sample        34.406            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.95                            sample        35.851            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.99                            sample        37.165            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.999                           sample        39.453            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p0.9999                          sample        39.453            ms/op
 * CollectorsVsStreamToList.viaStream:viaStream·p1.00                            sample        39.453            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                sample   69  339.465 ± 19.829   ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.00    sample       274.203            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.50    sample       329.777            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.90    sample       397.935            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.95    sample       449.577            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.99    sample       494.404            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.999   sample       494.404            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p0.9999  sample       494.404            ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel:viaCollectorsParallel·p1.00    sample       494.404            ms/op
 * CollectorsVsStreamToList.viaStreamParallel                                    sample   78  301.566 ± 24.886   ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.00            sample       229.638            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.50            sample       283.378            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.90            sample       416.966            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.95            sample       480.012            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.99            sample       516.948            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.999           sample       516.948            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p0.9999          sample       516.948            ms/op
 * CollectorsVsStreamToList.viaStreamParallel:viaStreamParallel·p1.00            sample       516.948            ms/op
 *
 * CollectorsVsStreamToList.viaCollectors                                            ss   20   85.415 ±  3.792   ms/op
 * CollectorsVsStreamToList.viaStream                                                ss   20   57.022 ±  1.502   ms/op
 * CollectorsVsStreamToList.viaCollectorsParallel                                    ss   20  512.169 ± 27.127   ms/op
 * CollectorsVsStreamToList.viaStreamParallel                                        ss   20  488.012 ± 35.937   ms/op
 */