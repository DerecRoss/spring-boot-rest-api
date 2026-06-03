package com.rest_api.spring_boot_rest_api.file.exporter.contract;

import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {
    Resource importFile(List<PersonDto> people) throws Exception;
}
