package com.rest_api.spring_boot_rest_api.file.exporter.factory;

import com.rest_api.spring_boot_rest_api.file.exporter.MediaTypes;
import com.rest_api.spring_boot_rest_api.file.exporter.contract.FileExporter;
import com.rest_api.spring_boot_rest_api.file.exporter.implementation.CsvExporter;
import com.rest_api.spring_boot_rest_api.file.exporter.implementation.XlsxExporter;
import com.rest_api.spring_boot_rest_api.file.importer.contract.FileImporter;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    // responsibility for chose if import used is CSV or XLS

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception{
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)){
            return context.getBean(XlsxExporter.class); // Spring IOC have responsibility for this instance of class
//          return new XlsxImporter(); not recommended in spring for dependency injection.
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
        } else {
            throw new BadRequestException("Invalid file extension.");
        }
    }
}
