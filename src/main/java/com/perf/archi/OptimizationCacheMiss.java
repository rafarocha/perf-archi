package com.perf.archi;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class OptimizationCacheMiss {

    private static long ops = 0;
    private static Instant t0 = Instant.now();

    public static void permute_loops_before(int size) {
        double x[][] = new double[size][size];
        for (int k = 0; k < 100; k = k+1)
            for (int j = 0; j < 100; j = j+1)
                for (int i = 0; i < 5000; i = i+1) {
                    x[i][j] = 2 * x[i][j]; ops++;
                }
    }

    public static void permute_loops_after(int size) {
        double x[][] = new double[size][size];
        for (int k = 0; k < 100; k = k+1)
            for (int i = 0; i < 5000; i = i+1)
                for (int j = 0; j < 100; j = j+1) {
                    x[i][j] = 2 * x[i][j]; ops++;
                }
    }

    public static void merge_arrays_before(int size) {
        int[] val = new int[size];
        int[] key = new int[size];

        for (int i = 0; i < val.length; i++) {
            val[i] += 5.3;
            key[i] *= 3 / val[i];
        }
    }

    public static void merge_arrays_after(int size) {
        Instant t1 = Instant.now();
        MergeArray[] merge = new MergeArray[size];
        for (int i = 0; i < merge.length; i++) {
            merge[i] = new MergeArray();
        }
        Duration duration = Duration.between(t1, Instant.now());
		System.out.println( "create merge_array_after  millisec " + duration.toMillis());
        
        for (int i = 0; i < merge.length; i++) {
            merge[i].val += 5.3;
            merge[i].key *= 3 / merge[i].val;
        }
    }

    public static void main(String[] args) {
        main_permute_loops();
    }

    private static void main_merge_arrays() {
        Instant t1 = Instant.now();
        merge_arrays_after(1000000);
		Duration duration = Duration.between(t1, Instant.now());
		System.out.println( "merge arrays after millisec " + duration.toMillis());
    }
    
    private static void main_permute_loops() {
        System.out.println( "ops, total_time, milisec" );
        for (int i = 0; i < 1000; i++) {
            Instant t1 = Instant.now();
            permute_loops_before(10000);
            Duration duration = Duration.between(t1, Instant.now());
            Duration durTotal = Duration.between(t0, Instant.now());            
            System.out.println( String.format("%s, %s, %s", ops, durTotal.toSeconds(), duration.toMillis()) );
        }
        System.out.println( "finish" );
    }
    
}