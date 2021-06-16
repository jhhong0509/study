package com.test.excel.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    void uploadExcelToDatabase(MultipartFile file);
}
