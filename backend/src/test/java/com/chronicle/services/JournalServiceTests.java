package com.chronicle.services;

import com.chronicle.TestConfig;
import com.chronicle.records.Config;
import com.chronicle.records.JournalEntry;
import com.chronicle.repositories.JournalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
public class JournalServiceTests {

    private JournalService journalService;
    private JournalRepository journalRepository;

    @Autowired
    private Config config;

    @BeforeEach
    void setup() {
        journalRepository = Mockito.mock(JournalRepository.class);
        journalService = new JournalService(journalRepository, config);
    }

    @Test
    void contextLoads() {
        assertThat(journalService).isNotNull();
    }

    @Test
    void callsRepositoryCorrectly() {
        LocalDate fixedTime = LocalDate.of(2013, 1, 14);

        try (
                MockedStatic<LocalDate> mocked = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)
        ) {
            mocked.when(LocalDate::now).thenReturn(fixedTime);
            journalService.add("test");

            Mockito.verify(journalRepository).add(
                    new JournalEntry("Monday 14-01-2013\n\ntest", "14-01-2013"),
                    "target/test-data/2013/January/"
            );

        }

    }
}
