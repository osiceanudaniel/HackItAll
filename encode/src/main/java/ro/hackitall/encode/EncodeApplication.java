package ro.hackitall.encode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EncodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncodeApplication.class, args);
		String x = "TEST";

		System.out.println(x);
	}
}
