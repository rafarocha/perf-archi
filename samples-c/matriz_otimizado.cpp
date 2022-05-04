#include <stdio.h>
#include <omp.h>
#include <math.h>
#include <ctime>

#include <chrono>
#include <iostream>



static long N = 1000;
static long M = 100000;
double step;

int original(){

	double a[N], b[N], c[N][N], d[N];	
	
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

int permuta_loops(){

	double a[N], b[N], c[N][N], d[N];	
	
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

int fusao_loops(){

	double a[N], b[N], c[N][N], d[N];	
	
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

int merge_arrays(){

	struct m_struct{
		double a, b, d;
	};


	double c[N][N];

	struct m_struct ms[N];	
	
	int i, j;
	
	/* Inicialização */
	for (i=0; i<N; i++){
		ms[i].a = i*5.3;
		ms[i].b = 0.8+i;
		ms[i].d = 0.1*i;
	}

	
	for (int k=0; k<M; k++)
	for (i=0; i<N; i++){
		if(k==0) ms[i].d = 1.0/ms[i].a;
		for (j=0; j<N; j++){
			c[i][j] = i*0.3;
			c[i][j] += k + (ms[i].b * ms[i].a)/(j+1);
		}
	}
	
		 
	return 1;	
}

int main (){

	// Versao original
	auto t1 = std::chrono::high_resolution_clock::now();
	original();
	auto t2 = std::chrono::high_resolution_clock::now();

    auto duration = (std::chrono::duration_cast<std::chrono::microseconds>( t2 - t1 ).count());
    float time = (float)duration/1000000;
    std::cout << "Versao original = " << time << " segundos." << std::endl;
	
	// Com permuta de Loops
	t1 = std::chrono::high_resolution_clock::now();
	permuta_loops();
	t2 = std::chrono::high_resolution_clock::now();

    duration = (std::chrono::duration_cast<std::chrono::microseconds>( t2 - t1 ).count());
    time = (float)duration/1000000;
    std::cout << "Versao após permuta de loops = " << time << " segundos." << std::endl;

    // Com fusao de Loops
	t1 = std::chrono::high_resolution_clock::now();
	fusao_loops();
	t2 = std::chrono::high_resolution_clock::now();

    duration = (std::chrono::duration_cast<std::chrono::microseconds>( t2 - t1 ).count());
    time = (float)duration/1000000;
    std::cout << "Versao após fusao de loops = " << time << " segundos." << std::endl;


 // Com fusao de Loops
	t1 = std::chrono::high_resolution_clock::now();
	merge_arrays();
	t2 = std::chrono::high_resolution_clock::now();

    duration = (std::chrono::duration_cast<std::chrono::microseconds>( t2 - t1 ).count());
    time = (float)duration/1000000;
    std::cout << "Versao após uniao de arrays = " << time << " segundos." << std::endl;


	return 1;
}
  

  

  
  