package com.backend.trainerbooks.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.backend.trainerbooks.repositorys")
@EnableCaching
public class JpaConfig {
}
