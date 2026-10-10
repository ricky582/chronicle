package com.chronicle.repositories;

import com.chronicle.records.JournalEntry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class JournalRepository {

    public String add(JournalEntry entryInfo, String path){
        try {
            String filename = String.format("%s.md", entryInfo.createdAt());

            Files.createDirectories(Paths.get(path));
            FileWriter fileWriter = new FileWriter(path + filename);

            fileWriter.write(entryInfo.content());
            fileWriter.close();

            return filename;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File creation failed");
        }
    }
}
