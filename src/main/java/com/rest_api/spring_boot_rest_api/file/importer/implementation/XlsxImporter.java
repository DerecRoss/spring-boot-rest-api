package com.rest_api.spring_boot_rest_api.file.importer.implementation;

import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsxImporter implements FileImporter {
    @Override
    public List<PersonDto> importFile(InputStream inputStream) throws Exception {

        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
//            XSSFSheet sheet = workbook.getSheet("data"); get via sheet name;
            if (rowIterator.hasNext()) rowIterator.next();

            return parseRowsToPersonDtoList(rowIterator);
        }
    }

    private List<PersonDto> parseRowsToPersonDtoList(Iterator<Row> rowIterator) {
        List<PersonDto> people = new ArrayList<>();

        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            if (isRowValid(row)){
                people.add(parseRowToPersonDto(row));
            }
        }

        return people;
    }

    private PersonDto parseRowToPersonDto(Row row) {
        PersonDto person = new PersonDto();
        person.setFirstName(row.getCell(0).getStringCellValue());
        person.setFirstName(row.getCell(1).getStringCellValue());
        person.setFirstName(row.getCell(2).getStringCellValue());
        person.setFirstName(row.getCell(3).getStringCellValue());
        person.setEnabled(true);
        return person;
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}
