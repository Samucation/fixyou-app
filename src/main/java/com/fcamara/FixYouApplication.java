package com.fcamara;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class FixYouApplication {

	private static final Logger LOGGER = LogManager.getLogger(FixYouApplication.class);

	@Value("${application.environment}")
	private String environmentType;

	public static void main(String[] args) {
		loadEnvironmentsConfigurations();
		SpringApplication.run(FixYouApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200", "http://localhost:8080/swagger-ui/index.html")
						.allowedMethods("GET", "POST", "PUT", "DELETE");
//						.allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
//						.allowCredentials(true);
			}
		};
	}

	private static void loadEnvironmentsConfigurations(){
		String environmentType = System.getenv("APPLICATION_ENVIRONMENT");

		if ("local".equalsIgnoreCase(environmentType)) {
			LOGGER.info("Carregando variáveis de ambiente do arquivo .env para ambiente local.");

			String envPath = System.getenv("ENV_PATH");
			String envFile = System.getenv("ENV_FILE");

			Dotenv dotenv = Dotenv.configure()
					.directory(envPath)
					.filename(envFile + ".env")
					.load();

			// Infra
			System.setProperty("server.port", dotenv.get("SERVER_PORT"));
			System.setProperty("APPLICATION_NAME", dotenv.get("APPLICATION_NAME"));

			// Database
			System.setProperty("spring.datasource.url", dotenv.get("JDBC_DATABASE_URL"));
			System.setProperty("spring.datasource.username", dotenv.get("JDBC_DATABASE_USERNAME"));
			System.setProperty("spring.datasource.password", dotenv.get("JDBC_DATABASE_PASSWORD"));
			System.setProperty("spring.jpa.hibernate.ddl-auto", dotenv.get("HIBERNATE_DDL_AUTO"));

			// JPA e Logs SQL
			System.setProperty("JPA_SHOW_SQL", dotenv.get("JPA_SHOW_SQL", "true"));
			System.setProperty("HIBERNATE_DIALECT", dotenv.get("HIBERNATE_DIALECT", "org.hibernate.dialect.PostgreSQLDialect"));
			System.setProperty("SQL_LOG_LEVEL", dotenv.get("SQL_LOG_LEVEL", "DEBUG"));
			System.setProperty("SQL_BIND_LOG_LEVEL", dotenv.get("SQL_BIND_LOG_LEVEL", "TRACE"));

			// Keycloak
			System.setProperty("KEYCLOAK_ISSUER_URI", dotenv.get("KEYCLOAK_ISSUER_URI"));
			System.setProperty("KEYCLOAK_JWK_SET_URI", dotenv.get("KEYCLOAK_JWK_SET_URI"));
			System.setProperty("CLIENT_VALUE", dotenv.get("CLIENT_VALUE"));

			// CORS
			System.setProperty("CORS_ALLOWED_LIST", dotenv.get("CORS_ALLOWED_LIST"));

			// Outros Logs
			System.setProperty("SECURITY_LOG_LEVEL", dotenv.get("SECURITY_LOG_LEVEL", "DEBUG"));
			System.setProperty("WEB_LOG_LEVEL", dotenv.get("WEB_LOG_LEVEL", "DEBUG"));

			System.setProperty("SPRINT_SECURITY_BASIC", dotenv.get("SPRINT_SECURITY_BASIC"));

			// Flyway
			System.setProperty("FLYWAY_CLEAN_DISABLED", dotenv.get("FLYWAY_CLEAN_DISABLED", "true"));

			LOGGER.info("Environments loaded do .env: JDBC_DATABASE_URL: [{}], JDBC_DATABASE_USERNAME: [{}], KEYCLOAK_ISSUER_URI: [{}], CORS_ALLOWED_LIST: [{}]",
					dotenv.get("JDBC_DATABASE_URL"),
					dotenv.get("JDBC_DATABASE_USERNAME"),
					dotenv.get("KEYCLOAK_ISSUER_URI"),
					dotenv.get("CORS_ALLOWED_LIST")
			);

		} else {
			LOGGER.info("Ambiente remoto detectado. Usando variáveis de ambiente do sistema.");

			// Infra
			System.setProperty("server.port", System.getenv("SERVER_PORT"));
			System.setProperty("APPLICATION_NAME", System.getenv("APPLICATION_NAME"));

			// Database
			System.setProperty("spring.datasource.url", System.getenv("JDBC_DATABASE_URL"));
			System.setProperty("spring.datasource.username", System.getenv("JDBC_DATABASE_USERNAME"));
			System.setProperty("spring.datasource.password", System.getenv("JDBC_DATABASE_PASSWORD"));
			System.setProperty("spring.jpa.hibernate.ddl-auto", System.getenv("HIBERNATE_DDL_AUTO"));

			// JPA e Logs SQL
			System.setProperty("JPA_SHOW_SQL", System.getenv("JPA_SHOW_SQL"));
			System.setProperty("HIBERNATE_DIALECT", System.getenv("HIBERNATE_DIALECT"));
			System.setProperty("SQL_LOG_LEVEL", System.getenv("SQL_LOG_LEVEL"));
			System.setProperty("SQL_BIND_LOG_LEVEL", System.getenv("SQL_BIND_LOG_LEVEL"));

			// Keycloak
			System.setProperty("KEYCLOAK_ISSUER_URI", System.getenv("KEYCLOAK_ISSUER_URI"));
			System.setProperty("KEYCLOAK_JWK_SET_URI", System.getenv("KEYCLOAK_JWK_SET_URI"));
			System.setProperty("CLIENT_VALUE", System.getenv("CLIENT_VALUE"));

			// CORS
			System.setProperty("CORS_ALLOWED_LIST", System.getenv("CORS_ALLOWED_LIST"));

			// Outros Logs
			System.setProperty("SECURITY_LOG_LEVEL", System.getenv("SECURITY_LOG_LEVEL"));
			System.setProperty("WEB_LOG_LEVEL", System.getenv("WEB_LOG_LEVEL"));

			// Flyway
			System.setProperty("FLYWAY_CLEAN_DISABLED", System.getenv("FLYWAY_CLEAN_DISABLED"));
			System.setProperty("SPRINT_SECURITY_BASIC", System.getenv("SPRINT_SECURITY_BASIC"));

			LOGGER.info("Environments loaded: JDBC_DATABASE_URL: [{}], JDBC_DATABASE_USERNAME: [{}], KEYCLOAK_ISSUER_URI: [{}], CORS_ALLOWED_LIST: [{}]",
					System.getenv("JDBC_DATABASE_URL"),
					System.getenv("JDBC_DATABASE_USERNAME"),
					System.getenv("KEYCLOAK_ISSUER_URI"),
					System.getenv("CORS_ALLOWED_LIST")
			);
		}
	}
}