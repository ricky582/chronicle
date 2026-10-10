package com.chronicle;

import com.chronicle.records.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Configuration
public class ConfigSingleton {

    @Bean
    public Config config() {
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");

        Map<String, String> obj = yaml.load(inputStream);

        return new Config(obj.get("journalPath"));

    }
}
