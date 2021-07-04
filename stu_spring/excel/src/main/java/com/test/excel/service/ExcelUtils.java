package com.test.excel.service;

import com.test.excel.entity.Test;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelUtils {
    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public boolean isExcel(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public List<Test> excelToTest(InputStream inputStream) {

        DataFormatter dataFormatter = new DataFormatter();      // to Change each value to String
        List<String> list = new ArrayList<>();
        List<Test> tests = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("Number Of Sheets: " + workbook.getNumberOfSheets());
            System.out.println("Number Of Columns: " + sheet.getRow(0).getLastCellNum());

            for (Row row : sheet) {
                for (Cell cell : row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    list.add(cellValue);
                }
                tests.add(getTestFromList(list));
                list = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tests;
    }

    private Test getTestFromList(List<String> value) {
        return Test.builder()
                .title(value.get(0))
                .description(value.get(1))
                .build();
    }

}
