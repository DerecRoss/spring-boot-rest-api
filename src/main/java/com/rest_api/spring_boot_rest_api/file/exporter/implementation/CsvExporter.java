package com.rest_api.spring_boot_rest_api.file.exporter.implementation;

import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.file.exporter.contract.FileExporter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvExporter implements FileExporter {
    @Override
    public Resource importFile(List<PersonDto> people) throws Exception {
        return null;
    }
}
