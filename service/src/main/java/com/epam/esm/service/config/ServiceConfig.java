package com.epam.esm.service.config;

import com.epam.esm.persistence.config.DBConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.epam.esm.service")
@Import(DBConfig.class)
public class ServiceConfig {
}
