package jmh;

import org.openjdk.jmh.annotations.Benchmark;

public class LambdaVsAnonymousClass {

    @Benchmark
    public static Level link() {
        Level prev = null;
        for (Level curr = LambdaVsAnonymousClass.get1024("str"); curr != null; curr = curr.up()) {
            prev = curr;
        }
        return prev;
    }

    public static Level get1023(String p) {
        return () -> null;
    }

    public static Level get1024s(String p) {
        return () -> get1023(p);
    }

    public static Level get1024(final String p) {
        return new Level() {
            @Override
            public Level up() {
                return get1023(p);
            }
        };
    }

    @FunctionalInterface
    public interface Level {

        Level up();
    }

}
