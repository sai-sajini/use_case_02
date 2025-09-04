package com.yourcompany.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestDataReader {
    private String filePath;

    public TestDataReader(String filePath) {
        this.filePath = filePath;
    }

    public List<Map<String, String>> getData(String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet " + sheetName + " does not exist in " + filePath);
            }
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.hasNext() ? rowIterator.next() : null;
            if (headerRow == null) {
                throw new RuntimeException("No header row found in sheet " + sheetName);
            }
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> dataMap = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i);
                    String value = getCellValue(cell);
                    dataMap.put(headers.get(i), value);
                }
                dataList.add(dataMap);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from: " + filePath, e);
        }
        return dataList;
    }

    public Map<String, String> getRowData(String sheetName, int rowIndex) {
        List<Map<String, String>> dataList = getData(sheetName);
        if (rowIndex < 0 || rowIndex >= dataList.size()) {
            throw new IndexOutOfBoundsException("Row " + rowIndex + " does not exist in sheet " + sheetName);
        }
        return dataList.get(rowIndex);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

}
