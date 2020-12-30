package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 25, time = 1)
public class ListVsMapForPriority {

    final List<String> types = Arrays
            .asList("Residence", "Correspondance", "Office", "Next Door", "Some", "Another");
    final Map<String, Integer> priorityMap = new HashMap<String, Integer>() {
        {
            put("Residence", 1);
            put("Correspondance", 2);
            put("Office", 3);
            put("Next Door", 4);
            put("Some", 5);
            put("Another", 6);
        }
    };
    List<Address> addresses = Arrays.asList(
            new Address(1, "Office", "AAAAA"),
            new Address(2, "Correspondence", "BBBBB"),
            new Address(3, "Residence", "CCCC"),
            new Address(4, "Next Door", "DDDD"));

    @Benchmark
    public void listPerform() {
        Optional<Address> address = addresses.stream()
                .filter(a -> types.contains(a.getType()))
                .min(Comparator.comparingInt(o -> types.indexOf(o.getType())));
    }

    @Benchmark
    public void mapPerform() {
        Optional<Address> address = addresses.stream()
                .filter(a -> priorityMap.containsKey(a.getType()))
                .min(Comparator.comparingInt(o -> priorityMap.get(o.getType())));
    }

    static class Address {
        int num;
        String type;
        String kind;

        public Address(int num, String type, String kind) {
            this.num = num;
            this.type = type;
            this.kind = kind;
        }

        public String getType() {
            return type;
        }
    }
}
