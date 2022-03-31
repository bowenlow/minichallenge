package com.challenge.project;

import com.challenge.model.Person;
import com.challenge.model.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    PersonRepository repository;
    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    public void save(MultipartFile file) {
        try {
            List<Person> persons = CSVHelper.csvToPerson(file.getInputStream());

            //
            repository.saveAll(persons);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
}
