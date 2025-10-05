package com.yogesh.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages = "com.yogesh")
@EnableAsync
public class AppConfig {
    // Main application configuration enabling component scanning and async support
}
