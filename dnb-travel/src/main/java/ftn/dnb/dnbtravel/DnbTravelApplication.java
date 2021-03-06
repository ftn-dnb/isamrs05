package ftn.dnb.dnbtravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class DnbTravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnbTravelApplication.class, args);
	}

}
