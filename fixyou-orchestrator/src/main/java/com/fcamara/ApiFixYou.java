package com.fcamara;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ApiFixYou {

	private static final Logger LOGGER = LogManager.getLogger(ApiFixYou.class);

	@Value("${application.environment}")
	private String environmentType;

	public static void main(String[] args) {
		loadEnvironmentsConfigurations();
		SpringApplication.run(ApiFixYou.class, args);
	}

	private static void loadEnvironmentsConfigurations() {
		String environmentType = System.getenv("APPLICATION_ENVIRONMENT");
		LOGGER.info("Valor lido de APPLICATION_ENVIRONMENT: [{}]", environmentType);

		if ("local".equalsIgnoreCase(environmentType) || "docker".equalsIgnoreCase(environmentType)) {
			LOGGER.info("Carregando variáveis de ambiente do arquivo .env para ambiente local.");

			String envPath = System.getenv("ENV_PATH");
			if (envPath == null || envPath.isBlank()) {
				envPath = new File("").getAbsolutePath();
				LOGGER.warn("ENV_PATH não definido. Usando raiz do projeto: [{}]", envPath);
			}

			String envFile = System.getenv("ENV_FILE");
			String fileName = (envFile == null || envFile.isBlank() || "DEFAULT_ENV".equalsIgnoreCase(envFile))
					? ".env"
					: envFile.endsWith(".env") ? envFile : envFile + ".env";

			LOGGER.info("Verificando ENV_PATH: [{}]", envPath);
			LOGGER.info("Verificando ENV_FILE: [{}]", fileName);

			File dotenvFile = new File(envPath + File.separator + fileName);
			if (!dotenvFile.exists()) {
				LOGGER.error("Arquivo .env não encontrado no caminho: [{}]", dotenvFile.getAbsolutePath());
				throw new RuntimeException("Arquivo .env não encontrado: " + dotenvFile.getAbsolutePath());
			}

			Dotenv dotenv = (envPath == null || envPath.isBlank() || envPath.equals("."))
					? Dotenv.configure().filename(fileName).load()
					: Dotenv.configure().directory(envPath).filename(fileName).load();

			setPropertyIfNotNull("server.port", sanitizeIfSensitiveKey("server.port", dotenv.get("SERVER_PORT")));
			setPropertyIfNotNull("APPLICATION_NAME", sanitizeIfSensitiveKey("APPLICATION_NAME", dotenv.get("APPLICATION_NAME")));

			setPropertyIfNotNull("spring.datasource.url", sanitizeIfSensitiveKey("spring.datasource.url", dotenv.get("JDBC_DATABASE_URL")));
			setPropertyIfNotNull("spring.datasource.username", sanitizeIfSensitiveKey("spring.datasource.username", dotenv.get("JDBC_DATABASE_USERNAME")));
			setPropertyIfNotNull("spring.datasource.password", sanitizeIfSensitiveKey("spring.datasource.password", dotenv.get("JDBC_DATABASE_PASSWORD")));

			setPropertyIfNotNull("spring.jpa.hibernate.ddl-auto", sanitizeIfSensitiveKey("spring.jpa.hibernate.ddl-auto", dotenv.get("HIBERNATE_DDL_AUTO")));

			setPropertyIfNotNull("JPA_SHOW_SQL", sanitizeIfSensitiveKey("JPA_SHOW_SQL", dotenv.get("JPA_SHOW_SQL", "true")));
			setPropertyIfNotNull("HIBERNATE_DIALECT", sanitizeIfSensitiveKey("HIBERNATE_DIALECT", dotenv.get("HIBERNATE_DIALECT", "org.hibernate.dialect.PostgreSQLDialect")));
			setPropertyIfNotNull("SQL_LOG_LEVEL", sanitizeIfSensitiveKey("SQL_LOG_LEVEL", dotenv.get("SQL_LOG_LEVEL", "DEBUG")));
			setPropertyIfNotNull("SQL_BIND_LOG_LEVEL", sanitizeIfSensitiveKey("SQL_BIND_LOG_LEVEL", dotenv.get("SQL_BIND_LOG_LEVEL", "TRACE")));

			setPropertyIfNotNull("KEYCLOAK_ISSUER_URI", sanitizeIfSensitiveKey("KEYCLOAK_ISSUER_URI", dotenv.get("KEYCLOAK_ISSUER_URI")));
			setPropertyIfNotNull("KEYCLOAK_JWK_SET_URI", sanitizeIfSensitiveKey("KEYCLOAK_JWK_SET_URI", dotenv.get("KEYCLOAK_JWK_SET_URI")));
			setPropertyIfNotNull("KEYCLOAK_CLIENT_ID", sanitizeIfSensitiveKey("KEYCLOAK_CLIENT_ID", dotenv.get("KEYCLOAK_CLIENT_ID")));
			setPropertyIfNotNull("KEYCLOAK_CLIENT_SECRET", sanitizeIfSensitiveKey("KEYCLOAK_CLIENT_SECRET", dotenv.get("KEYCLOAK_CLIENT_SECRET")));

			setPropertyIfNotNull("CORS_ALLOWED_LIST", sanitizeIfSensitiveKey("CORS_ALLOWED_LIST", dotenv.get("CORS_ALLOWED_LIST")));

			setPropertyIfNotNull("SECURITY_LOG_LEVEL", sanitizeIfSensitiveKey("SECURITY_LOG_LEVEL", dotenv.get("SECURITY_LOG_LEVEL", "DEBUG")));
			setPropertyIfNotNull("WEB_LOG_LEVEL", sanitizeIfSensitiveKey("WEB_LOG_LEVEL", dotenv.get("WEB_LOG_LEVEL", "DEBUG")));

			setPropertyIfNotNull("SPRINT_SECURITY_BASIC", sanitizeIfSensitiveKey("SPRINT_SECURITY_BASIC", dotenv.get("SPRINT_SECURITY_BASIC")));
			setPropertyIfNotNull("FLYWAY_CLEAN_DISABLED", sanitizeIfSensitiveKey("FLYWAY_CLEAN_DISABLED", dotenv.get("FLYWAY_CLEAN_DISABLED", "true")));

			LOGGER.info("Environments loaded do .env: JDBC_DATABASE_URL: [{}], JDBC_DATABASE_USERNAME: [{}], KEYCLOAK_ISSUER_URI: [{}], CORS_ALLOWED_LIST: [{}]",
					dotenv.get("JDBC_DATABASE_URL"),
					dotenv.get("JDBC_DATABASE_USERNAME"),
					dotenv.get("KEYCLOAK_ISSUER_URI"),
					dotenv.get("CORS_ALLOWED_LIST"));

		} else {
			LOGGER.info("Ambiente remoto detectado [CLOUD MODE]. Usando variáveis de ambiente internas do sistema, será necessário definir as enriroments.");

			setPropertyIfNotNull("server.port", sanitizeIfSensitiveKey("server.port", System.getenv("SERVER_PORT")));
			setPropertyIfNotNull("APPLICATION_NAME", sanitizeIfSensitiveKey("APPLICATION_NAME", System.getenv("APPLICATION_NAME")));

			setPropertyIfNotNull("spring.datasource.url", sanitizeIfSensitiveKey("spring.datasource.url", System.getenv("JDBC_DATABASE_URL")));
			setPropertyIfNotNull("spring.datasource.username", sanitizeIfSensitiveKey("spring.datasource.username", System.getenv("JDBC_DATABASE_USERNAME")));
			setPropertyIfNotNull("spring.datasource.password", sanitizeIfSensitiveKey("spring.datasource.password", System.getenv("JDBC_DATABASE_PASSWORD")));

			setPropertyIfNotNull("spring.jpa.hibernate.ddl-auto", sanitizeIfSensitiveKey("spring.jpa.hibernate.ddl-auto", System.getenv("HIBERNATE_DDL_AUTO")));

			setPropertyIfNotNull("JPA_SHOW_SQL", sanitizeIfSensitiveKey("JPA_SHOW_SQL", System.getenv("JPA_SHOW_SQL")));
			setPropertyIfNotNull("HIBERNATE_DIALECT", sanitizeIfSensitiveKey("HIBERNATE_DIALECT", System.getenv("HIBERNATE_DIALECT")));
			setPropertyIfNotNull("SQL_LOG_LEVEL", sanitizeIfSensitiveKey("SQL_LOG_LEVEL", System.getenv("SQL_LOG_LEVEL")));
			setPropertyIfNotNull("SQL_BIND_LOG_LEVEL", sanitizeIfSensitiveKey("SQL_BIND_LOG_LEVEL", System.getenv("SQL_BIND_LOG_LEVEL")));

			setPropertyIfNotNull("KEYCLOAK_ISSUER_URI", sanitizeIfSensitiveKey("KEYCLOAK_ISSUER_URI", System.getenv("KEYCLOAK_ISSUER_URI")));
			setPropertyIfNotNull("KEYCLOAK_JWK_SET_URI", sanitizeIfSensitiveKey("KEYCLOAK_JWK_SET_URI", System.getenv("KEYCLOAK_JWK_SET_URI")));
			setPropertyIfNotNull("KEYCLOAK_CLIENT_ID", sanitizeIfSensitiveKey("KEYCLOAK_CLIENT_ID", System.getenv("KEYCLOAK_CLIENT_ID")));
			setPropertyIfNotNull("KEYCLOAK_CLIENT_SECRET", sanitizeIfSensitiveKey("KEYCLOAK_CLIENT_SECRET", System.getenv("KEYCLOAK_CLIENT_SECRET")));

			setPropertyIfNotNull("CORS_ALLOWED_LIST", sanitizeIfSensitiveKey("CORS_ALLOWED_LIST", System.getenv("CORS_ALLOWED_LIST")));
			setPropertyIfNotNull("SECURITY_LOG_LEVEL", sanitizeIfSensitiveKey("SECURITY_LOG_LEVEL", System.getenv("SECURITY_LOG_LEVEL")));
			setPropertyIfNotNull("WEB_LOG_LEVEL", sanitizeIfSensitiveKey("WEB_LOG_LEVEL", System.getenv("WEB_LOG_LEVEL")));
			setPropertyIfNotNull("FLYWAY_CLEAN_DISABLED", sanitizeIfSensitiveKey("FLYWAY_CLEAN_DISABLED", System.getenv("FLYWAY_CLEAN_DISABLED")));
			setPropertyIfNotNull("SPRINT_SECURITY_BASIC", sanitizeIfSensitiveKey("SPRINT_SECURITY_BASIC", System.getenv("SPRINT_SECURITY_BASIC")));

			LOGGER.info("Environments loaded: JDBC_DATABASE_URL: [{}], JDBC_DATABASE_USERNAME: [{}], KEYCLOAK_ISSUER_URI: [{}], CORS_ALLOWED_LIST: [{}]",
					System.getenv("JDBC_DATABASE_URL"),
					System.getenv("JDBC_DATABASE_USERNAME"),
					System.getenv("KEYCLOAK_ISSUER_URI"),
					System.getenv("CORS_ALLOWED_LIST"));
		}
	}

	private static void setPropertyIfNotNull(String key, String value) {
		if (value != null) {
			System.setProperty(key, value);
		} else {
			LOGGER.warn("Variável de ambiente [{}] está ausente ou nula. Verifique o arquivo .env ou as variáveis do sistema.", key);
		}
	}

	private static String sanitizeIfSensitiveKey(String key, String value) {
		if (value == null) return null;
		String lowercaseKey = key.toLowerCase();
		if (lowercaseKey.contains("password") || lowercaseKey.contains("secret")) {
			return value.replaceAll("^\"|\"$", "");
		}
		return value;
	}
}
