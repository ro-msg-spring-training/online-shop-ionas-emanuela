package ro.msg.learning.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ro.msg.learning.repositories")
@EntityScan(basePackages = {"ro.msg.learning.entities"})
@ComponentScan(basePackages = {"ro.msg.learning"})
public class ShopApplication {

	public static void main(String[] args) {

		SpringApplication.run(ShopApplication.class, args);
	}

}
