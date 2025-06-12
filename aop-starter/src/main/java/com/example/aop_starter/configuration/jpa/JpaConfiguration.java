package com.example.aop_starter.configuration.jpa;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnClass(JpaRepository.class)
@EnableJpaRepositories(basePackages = "com.example.aop_starter.repository")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class JpaConfiguration {
}
