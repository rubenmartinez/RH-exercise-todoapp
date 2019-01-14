package net.rubenmartinez.rhe.app;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Entrypoint for the exercise. It will start a Spring Boot application
 * initializing all controllers on subpackage <code>controller</code>
 * 
 * @author Rubén Martínez {@literal <ruben.martinez.olivares@gmail.com>}
 */
@SpringBootApplication
@EnableFeignClients
public class Main implements ApplicationListener<ApplicationReadyEvent> {
	
	@Value("${server.port}")
	private String serverPort;

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@PostConstruct
	public void init() {
		LOGGER.info("Initialization in progress. Please wait...");
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logBanner();
	}
	
	private void logBanner() {
		final String lineSep = System.getProperty("line.separator"); 
		
		LOGGER.info("{}{}{}=================================================================================="
				   + "{}>>>>>>>>>>>>>>>>>>>> Server ready at http://127.0.0.1:{}/ <<<<<<<<<<<<<<<<<<<<"
				   + "{}=================================================================================={}{}", lineSep, lineSep, lineSep, lineSep, serverPort, lineSep, lineSep, lineSep);
	}
}