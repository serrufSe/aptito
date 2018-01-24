package serruf.aptito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
		@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
		@PropertySource(value = "file:/etc/aptito/application.properties", ignoreResourceNotFound = true)
})
public class AptitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AptitoApplication.class, args);
	}
}
