package com.rest_api.spring_boot_rest_api.file.importer.contract;

import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDto> importFile(InputStream inputStream) throws Exception;
}
