package com.chronicle;

import com.chronicle.records.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Import(TestConfig.class)
class ConfigSingletonTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Config config;

    @Test
    void isSingleton() {
        Config config1 = context.getBean(Config.class);
        Config config2 = context.getBean(Config.class);

        assertSame(config1, config2);
    }

    @Test
    void testConfigIsSet() {
        assertThat(config.journalPath()).isEqualTo("target/test-data");
    }

}