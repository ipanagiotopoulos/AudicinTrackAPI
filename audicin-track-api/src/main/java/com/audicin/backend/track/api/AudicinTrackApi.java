package com.audicin.backend.track.api;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class AudicinTrackApi {


	@Bean
	public DataSource dataSource(final @Value("${spring.datasource.driver-class-name}") String driverClassName,
								 final @Value("${spring.datasource.url}") String url,
								 final @Value("${spring.datasource.username}") String username,
								 final @Value("${spring.datasource.password}") String password) {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(AudicinTrackApi.class, args);
	}
}
