package com.challenge.project;

import com.challenge.model.Person;
import com.challenge.project.CSVHelper;
import com.challenge.project.CSVService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    CSVService fileService;

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findPerson(@RequestParam(name="min", defaultValue = "0.0") double minSalary,
                                                      @RequestParam(name="max", defaultValue = "4000.0") double maxSalary,
                                                      @RequestParam(defaultValue = "0") int offset,
                                                      @RequestParam(defaultValue = "-1") int limit,
                                                      @RequestParam(name="sort", defaultValue = "-1") String sortColumn){
        PersonSpecification minSpec = new PersonSpecification(new SearchCriteria("salary",">",minSalary));
        PersonSpecification maxSpec = new PersonSpecification(new SearchCriteria("salary", "<", maxSalary));
        List<Person> results;
        if (sortColumn.compareTo("-1") != 0 && Arrays.stream(CSVHelper.headers).anyMatch(sortColumn::equals)) {
            results = fileService.repository.findAll(Specification.where(minSpec).and(maxSpec),
                    Sort.by(Sort.Direction.ASC,sortColumn));
        } else {
            results = fileService.repository.findAll(Specification.where(minSpec).and(maxSpec));
        }

        List<Person> new_results;
        if (limit > 0) {
            new_results = results.subList(Math.min(results.size(), offset),
                    Math.min(results.size(), offset + limit));
        }else{
            new_results = results.subList(Math.min(results.size(), offset),
                    results.size());
        }

        return(ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Query Complete",1,new_results)));

    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> upload(@RequestParam(name = "file") MultipartFile file){
        String message = "";
        if (CSVHelper.hasCSVFormat(file)){
            try {
                fileService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return (ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage(message, 1, null)));
            } catch (Exception e) {
                message = "Could not upload the file: " + e.getMessage() + "!";
                return (ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage(message, 0, null )));
            }
        }
        // Please upload a csv file
        message = "Please upload a csv file!";
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage(message, 0, null)));
    }

}
