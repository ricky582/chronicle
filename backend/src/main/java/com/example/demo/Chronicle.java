package com.example.demo;
import com.example.demo.services.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Chronicle {
	@Autowired
	private JournalService journalService;
	private static final Logger logger = LoggerFactory.getLogger(Chronicle.class);

	static void main(String[] args) {
		SpringApplication.run(Chronicle.class, args);
	}

	@PostMapping("/entry")
	public String addEntry(@RequestBody String entry) {
		String filename = journalService.add(entry);
		logger.info("New journal entry created: {}", filename);

		return filename;
	}
}