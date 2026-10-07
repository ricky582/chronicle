package com.example.demo.repositories;

import com.example.demo.TestConfig;
import com.example.demo.records.Config;
import com.example.demo.records.JournalEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Import(TestConfig.class)
public class JournalRepositoryTests {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private Config config;

    @Test
    void contextLoads() {
        assertThat(journalRepository).isNotNull();
    }

    @Test
    void fileIsCreated() {
        journalRepository.add(new JournalEntry("test", "06-07-2016"), "target/test-data/new/");

        File file = new File(config.journalPath() + "/new/06-07-2016.md");
        assertTrue(file.exists());

        try (FileReader fileReader = new FileReader(file)) {
            String content = fileReader.readAllAsString();

            assertThat(content).isEqualTo("test");

        } catch (IOException e) {
            fail("Could not read file", e);
        }

        boolean deletionSuccessful = file.delete();
        assertTrue(deletionSuccessful);

        try {
            boolean pathDeletionSuccessful = Files.deleteIfExists(Path.of(config.journalPath() + "/new"));
            assertTrue(pathDeletionSuccessful);
        } catch (IOException e) {
            fail("Could not delete path", e);
        }
    }
}
