package com.perf.archi;

import java.time.Duration;
import java.time.Instant;

public class Blocagem {

    public static void  before(int N, int B) {
        int x[][] = new int[N][N];
        int y[][] = new int[N][N];
        int z[][] = new int[N][N];

        for (int i = 0; i < N; i = i+1)
            for (int j = 0; j < N; j = j+1) {
                int r = 0;
                for (int k = 0; k < N; k = k+1){
                    r = r + y[i][k] * z[k][j];
                }
                x[i][j] = r;
            }
    }

    public static void main(String[] args) {
        Instant t1 = Instant.now();
        before(5000, 10);
        Duration duration = Duration.between(t1, Instant.now());
		System.out.println( "finish " + duration.toMillis());
    }

    public static void after(int N, int B) {
        int x[][] = new int[N][N];
        int y[][] = new int[N][N];
        int z[][] = new int[N][N];
        
        for (int jj = 0; jj < N; jj = jj+B)
            for (int kk = 0; kk < N; kk = kk+B)
                for (int i = 0; i < N; i = i+1)
                    for (int j = jj; j < min(jj+B-1,N); j = j+1){
                        int r = 0;
                        for (int k = kk; k < min(kk+B-1,N); k = k+1) {
                            r = r + y[i][k]*z[k][j];
                        }
                        x[i][j] = x[i][j] + r;
                    }
    }

    public static int min(int x, int y) {
        return (x < y) ? x : y;
    }

}