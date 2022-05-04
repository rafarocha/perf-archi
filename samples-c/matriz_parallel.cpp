#include <stdio.h>
#include <omp.h>
#include <ctime>
#include <chrono>
#include <iostream>



static long N = 100000;
double step;

int main (){

	double a[N], b[N], c[N], d[N];	
	
	int i, j;
	
	auto t1 = std::chrono::high_resolution_clock::now();
    

	#pragma omp parallel default(none) shared(N,a,b,c,d) private(i, j)
	{
		#pragma omp for
		for (i=0; i<N; i++){
			a[i] = i*5.3;
			b[i] = 0.8;
			c[i] = i/2.2;
			d[i] = 0.1;
		}

		#pragma omp for nowait
		for (i=0; i<N-2; i++)
			for (j=0; j<N-2; j++)
				b[i] = (b[i] * a[i+1])/2;
	
		#pragma omp for nowait
		for (i=0; i<N; i++)
			d[i] = 1.0/c[i];

	
	} /*-- End of parallel region --*/
	

	auto t2 = std::chrono::high_resolution_clock::now();

    auto duration = (std::chrono::duration_cast<std::chrono::microseconds>( t2 - t1 ).count());

    float time = (float)duration/1000000;

    std::cout << "Tempo de processamento = " << time << " segundos." << std::endl;
	
	
	printf("a[0] = %f   d[0] = %f     a[N-1] = %f   d[N-1] = %f", a[0], d[0], a[N-1], d[N-1]);

	
}

  

  

  
  