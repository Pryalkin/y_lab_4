package com.pryalkin;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run() {
		return args -> {
			Connection conn = null;
			String url = null;
			String username = null;
			String password = null;
			try (InputStream in = SecurityApplication.class.getClassLoader().getResourceAsStream("application.yml")) {
				Yaml yaml = new Yaml();
				Map<String, Map<String, Map<String, String>>> data = yaml.load(in);
				url = data.get("spring").get("datasource").get("url");
				username = data.get("spring").get("datasource").get("username");
				password = data.get("spring").get("datasource").get("password");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(url, username, password);
				JdbcConnection jdbcConnection = new JdbcConnection(conn);
				Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
				Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
				liquibase.update();
				System.out.println("Migration is completed successfully");
			} catch (SQLException | ClassNotFoundException | LiquibaseException e) {
				e.printStackTrace();
			}
			finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}



}
