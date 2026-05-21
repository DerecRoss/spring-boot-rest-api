package com.rest_api.spring_boot_rest_api.file.importer.factory;

import com.rest_api.spring_boot_rest_api.file.importer.contract.FileImporter;
import com.rest_api.spring_boot_rest_api.file.importer.implementation.CsvImporter;
import com.rest_api.spring_boot_rest_api.file.importer.implementation.XlsxImporter;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    // responsibility for chose if import used is CSV or XLS

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception{
        if (fileName.endsWith(".xlsx")){
            return context.getBean(XlsxImporter.class); // Spring IOC have responsibility for this instance of class
//          return new XlsxImporter(); not recommended in spring for dependency injection.
        } else if (fileName.endsWith(".csv")) {
            return context.getBean(CsvImporter.class);
        } else {
            throw new BadRequestException("Invalid file extension.");
        }
    }
}
