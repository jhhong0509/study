package com.test.excel.service;

import com.test.excel.entity.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ExcelServiceImpl implements ExcelService {

    private final TestRepository testRepository;
    private final ExcelUtils excelUtils;

    @Override
    public void uploadExcelToDatabase(MultipartFile file) {
        if(!excelUtils.isExcel(file)) {
            throw new RuntimeException("File Type Mismatch");
        }

        try {
            testRepository.saveAll(excelUtils.excelToTest(file.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException("IO Exception");
        }

    }
}
