package jmh;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.hadoop.hbase.util.Bytes;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
public class BytesToLongConversion {

    @Param({"1597064529541", "1597219312794", "1597220926618"})
    private String timestamp;

//  public static void main(String[] args) throws IOException {
//    String[] timestamps = {"1597064529541", "1597219312794", "1597220926618"};
//    for (String timestamp : timestamps) {
//      System.out.println(timestamp);
//      byte[] bytes = timestamp.getBytes();
//      byte[] sm = Bytes.toBytes(1597064529541L);
//      byte[] sm1 = Bytes.toBytes(999);
//      byte[] sm2 = "999".getBytes();
//      byte[] str = "1597064529541".getBytes();
//      System.out.println(new String(str));
//      System.out.println(Bytes.toString(str));
//      System.out.println(new String(sm2));
//      System.out.println(Bytes.toString(sm2));
//
//      System.out.println(Bytes.toLong(bytes));
//      System.out.println(Bytes.toString(bytes));
//      System.out.println(Longs.fromByteArray(bytes));
//      Long jackson = new ObjectMapper().readValue(bytes, new TypeReference<Long>() {
//      });
//      System.out.println(jackson);
//      System.out.println(bytesToLong(bytes, 0));
//    }
//  }

    public static long bytesToLong(final byte[] bytes, final int offset) {
        long result = 0;
        for (int i = offset; i < Long.BYTES + offset; i++) {
            result <<= Long.BYTES;
            result |= (bytes[i] & 0xFF);
        }
        return result;
    }

    @Benchmark
    public Long jacksonForLong() {
        return ObjectMapperFactory.readValue(timestamp.getBytes(), new TypeReference<Long>() {
        });
    }

    @Benchmark
    public Long hbaseForLong() {
        return Bytes.toLong(timestamp.getBytes());
    }

    @Benchmark
    public Long hbaseForLongViaString() {
        return Long.parseLong(Bytes.toString(timestamp.getBytes()));
    }

    @Benchmark
    public Long hbaseForLongString() {
        return Long.parseLong(Bytes.toString(timestamp.getBytes()), 10);
    }

    public Long utilForLong() {
        return bytesToLong(timestamp.getBytes(), 0);
    }
}