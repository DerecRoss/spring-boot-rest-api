package com.rest_api.spring_boot_rest_api.file.importer.implementation;

import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {
    @Override
    public List<PersonDto> importFile(InputStream inputStream) throws Exception {

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        // format CSV for list of records
        return parseRecordsToPersonDto(records);
    }

    private List<PersonDto> parseRecordsToPersonDto(Iterable<CSVRecord> records) {
        List<PersonDto> people = new ArrayList<>();
        for (CSVRecord record : records){
            PersonDto person = new PersonDto();
            person.setFirstName(record.get("first_name"));
            person.setFirstName(record.get("last_name"));
            person.setFirstName(record.get("adress"));
            person.setFirstName(record.get("gender"));
            person.setEnabled(true);
            people.add(person);
        }
        return people;
    }
}
