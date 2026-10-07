package com.example.demo;

import com.example.demo.records.Config;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Import(TestConfig.class)
class ChronicleTests {

	@Autowired
	private Chronicle chronicle;

	@Autowired
	private Config config;

	@Test
	void contextLoads() {
		assertThat(chronicle).isNotNull();
	}

	@Test
	void entryIsCreated() {
		LocalDate fixedTime = LocalDate.of(2016, 6, 4);

		try (
			MockedStatic<LocalDate> mocked = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)
		) {
			mocked.when(LocalDate::now).thenReturn(fixedTime);

			chronicle.addEntry("test1");

			File file = new File(config.journalPath() + "/2016/June/04-06-2016.md");
			assertTrue(file.exists());

			try (FileReader fileReader = new FileReader(file)) {
				String content = fileReader.readAllAsString();

				assertThat(content).isEqualTo("Saturday 04-06-2016\n\ntest1");
			}

			boolean deletionSuccessful = file.delete();
			assertTrue(deletionSuccessful);
		} catch (IOException e) {
			fail("Could not read journal file", e);
        }

    }

}
