package com.perf.archi;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class PerfArchiApplication implements CommandLineRunner {

	@Autowired
	private MatrixFusionMergeService matrix;

	public static void main(String[] args) {
		SpringApplication.run(PerfArchiApplication.class, args);
		List<String> argumentos = new ArrayList<String>(Arrays.asList(args));
		//app.run(argumentos.stream().toArray(String[]::new));
	}

	@Override
	public void run(String... args) throws Exception {
		runAllServices();
	}

	private void runAllServices() {
		// original
		Instant t1 = Instant.now();
		int result = matrix.original(1000, 1000);
		Duration duration = Duration.between(t1, Instant.now());
		System.out.println( "original millisec " + duration.toMillis());

		// permuta
		t1 = Instant.now();
		result = matrix.permuta_loops(1000, 1000);
		duration = Duration.between(t1, Instant.now());
		System.out.println( "permuta_loops millisec " + duration.toMillis());

		// fusao loops
		t1 = Instant.now();
		result = matrix.fusao_loops(1000, 1000);
		duration = Duration.between(t1, Instant.now());
		System.out.println( "fusao_loops millisec " + duration.toMillis());
	}

}