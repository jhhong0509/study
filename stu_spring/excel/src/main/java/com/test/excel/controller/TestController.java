package com.test.excel.controller;

import com.test.excel.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final ExcelService excelService;

    @PostMapping("/test")
    public void saveExcel(@RequestParam MultipartFile file) {
        excelService.uploadExcelToDatabase(file);
    }
}
