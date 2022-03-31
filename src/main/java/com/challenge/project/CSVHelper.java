package com.challenge.project;

import com.challenge.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    static Logger logger = LoggerFactory.getLogger(LoggingController.class);
    public static String type = "text/csv";
    static String[] headers = {"name","salary"};
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!type.equals(file.getContentType())){
            return false;
        }
        return true;
    }

    public static InputStreamReader newReader(InputStream inputStream) {
        return new InputStreamReader(new BOMInputStream(inputStream), StandardCharsets.UTF_8);
    }
    public static List<Person> csvToPerson(InputStream is){
        InputStreamReader freader = newReader(is);
        try (BufferedReader fileReader = new BufferedReader(freader);
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withHeader(headers)
                             .withFirstRecordAsHeader()
                             .withIgnoreHeaderCase()
                             .withTrim());) {
            List<Person> persons = new ArrayList<Person>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                String n = csvRecord.get("name");
                double s =  Double.parseDouble(csvRecord.get("salary"));
                // Person filtering logic to be placed here:
                if (n.length()>0 && s >= 0.0) {
                    Person person = new Person(n,s);
                    persons.add(person);
                }
            }

            return persons;
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
