package org.wildhamsters.battleships;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BattleshipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipsApplication.class, args);
	}

}
