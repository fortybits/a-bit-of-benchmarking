package jmh;

import com.google.common.base.Splitter;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 1, batchSize = 1000)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 2, batchSize = 1000)
public class SplitterBenchmark {

    static final String ROWKEY_DELIMITER = ":";

    @Param({"0a_userId:productIdt1", "3x_userId:productIdt0"})
    private String input;

    @Benchmark
    public RowKey guavaSplitter() {
        List<String> list = Splitter.on(ROWKEY_DELIMITER).splitToList(input);
        if (list.size() != 2) {
            throw new IllegalArgumentException("some text!");
        }
        return new RowKey(list.get(0), list.get(1));
    }

    @Benchmark
    public RowKey javaSplitter() {
        String[] list = input.split(ROWKEY_DELIMITER);
        if (list.length != 2) {
            throw new IllegalArgumentException("some text!");
        }
        return new RowKey(list[0], list[1]);
    }

    static class RowKey {

        private String userId;
        private String productId;

        public RowKey(String userId, String productId) {
            this.userId = userId;
            this.productId = productId;
        }
    }
}