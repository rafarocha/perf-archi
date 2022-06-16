import java.time.Duration;
import java.time.Instant;

public class VerifiablePerformanceGraalVM {

    class Struct {
        public double a, b, d;
    }

    public static void main(String[] args) {
        
        // original
		Instant t1 = Instant.now();
		int result = original(1000, 1000);
		Duration duration = Duration.between(t1, Instant.now());
		System.out.println( "original millisec " + duration.toMillis());

		// permuta
		t1 = Instant.now();
		result = permuta_loops(1000, 1000);
		duration = Duration.between(t1, Instant.now());
		System.out.println( "permuta_loops millisec " + duration.toMillis());

		// fusao loops
		t1 = Instant.now();
		result = fusao_loops(1000, 1000);
		duration = Duration.between(t1, Instant.now());
		System.out.println( "fusao_loops millisec " + duration.toMillis());

    }

    public static int original(int N, int M) {
        double a[] = new double[N], b[] = new double[N], d[] = new double[N];
        double c[][] = new double[N][N];
	
        int i, j;
        
        /* Inicialização */
        for (i=0; i<N; i++){
            a[i] = i*5.3;
            b[i] = 0.8+i;
            d[i] = 0.1*i;
        }

        /* Inicialização */
        for (j=0; j<N; j++)
            for (i=0; i<N; i++)
                c[i][j] = i*0.3;
        
        
        for (int k=0; k<M; k++)
            for (j=0; j<N; j++)
                for (i=0; i<N; i++)
                    c[i][j] += k + (b[i] * a[i])/(j+1);
        
        for (i=0; i<N; i++)
            d[i] = 1.0/a[i];
        
        return 1;	
    }

    public static int permuta_loops(int N, int M) {
        double a[] = new double[N], b[] = new double[N], d[] = new double[N];
        double c[][] = new double[N][N];	
        
        int i, j;
        
        /* Inicialização */
        for (i=0; i<N; i++){
            a[i] = i*5.3;
            b[i] = 0.8+i;
            d[i] = 0.1*i;
        }
    
        /* Inicialização */
        for (i=0; i<N; i++)
            for (j=0; j<N; j++)
                c[i][j] = i*0.3;
        
        
        for (int k=0; k<M; k++)
            for (i=0; i<N; i++)
                for (j=0; j<N; j++)
                    c[i][j] += k + (b[i] * a[i])/(j+1);
        
        for (i=0; i<N; i++)
            d[i] = 1.0/a[i];
         
        return 1;	
    }

    public static int fusao_loops(int N, int M) {
        double a[] = new double[N], b[] = new double[N], d[] = new double[N];
        double c[][] = new double[N][N];
        
        int i, j;
        
        /* Inicialização */
        for (i=0; i<N; i++){
            a[i] = i*5.3;
            b[i] = 0.8+i;
            d[i] = 0.1*i;
        }
    
        for (int k=0; k<M; k++)
        for (i=0; i<N; i++){
            if(k==0) d[i] = 1.0/a[i];
            for (j=0; j<N; j++){
                c[i][j] = i*0.3;
                c[i][j] += k + (b[i] * a[i])/(j+1);
            }
        }
        
             
        return 1;	
    }
    
    public static int merge_arrays(int N, int M) {
        double a[] = new double[N], b[] = new double[N], d[] = new double[N];
        double c[][] = new double[N][N];
        
        Struct ms[] = new Struct[N];
        
        int i, j;
        /* Inicialização */
        for (i=0; i<N; i++) {
            ms[i].a = i*5.3;
            ms[i].b = 0.8+i;
            ms[i].d = 0.1*i;
        }
        
        for (int k=0; k<M; k++)
            for (i=0; i<N; i++) {
                if(k==0) ms[i].d = 1.0/ms[i].a;
                for (j=0; j<N; j++) {
                    c[i][j] = i*0.3;
                c[i][j] += k + (ms[i].b * ms[i].a)/(j+1);
                }
            }
        
             
        return 1;	
    }
    
}