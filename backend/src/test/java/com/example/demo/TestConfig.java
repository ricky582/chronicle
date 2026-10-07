package com.example.demo;

import com.example.demo.records.Config;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    Config testConfig() {
        return new Config("target/test-data");
    }
}
