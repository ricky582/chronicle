package com.example.demo.services;

import com.example.demo.records.Config;
import com.example.demo.records.JournalEntry;
import com.example.demo.repositories.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final Config config;

    public JournalService(JournalRepository journalRepository, Config config) {
        this.journalRepository = journalRepository;
        this.config = config;
    }

    public String add(String entry){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String currentDateFormatted = dateFormatter.format(currentDate);
        entry = StringUtils.capitalize(currentDate.getDayOfWeek().toString().toLowerCase()) +
                " " + currentDateFormatted + "\n\n" + entry;

        return this.journalRepository.add(
                new JournalEntry(entry, currentDateFormatted),
                String.format(
                        "%s/%s/%s/",
                        config.journalPath(),
                        currentDate.getYear(),
                        StringUtils.capitalize(currentDate.getMonth().toString().toLowerCase())
                )
        );
    }

}