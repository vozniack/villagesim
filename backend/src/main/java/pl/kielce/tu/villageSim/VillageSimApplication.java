package pl.kielce.tu.villageSim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VillageSimApplication {

	public static void main(String[] args) {
		SpringApplication.run(VillageSimApplication.class, args);
	}

}
