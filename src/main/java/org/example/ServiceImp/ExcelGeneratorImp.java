package org.example.ServiceImp;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ExcelGeneratorImp {
    private final String TITLE = "Traffic Report";
    private final String FIRST_SHEET = "First Sheet";
    private final String SECOND_SHEET = "Second Sheet";
    private final String THIRD_SHEET = "Third Sheet";
    public void generateExcelFile() {
        String folderPath = System.getProperty("user.home") + "/Documents/";
        String filePath = folderPath + "traffic_report.xlsx";
        Workbook workbook = new XSSFWorkbook(); // Create a new Excel workbook

        createFirstSheet(workbook);
        createSecondSheet(workbook);
        createThirdSheet(workbook);

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream); // Save the workbook to the file
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close(); // Close the workbook to release resources
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFirstSheet( Workbook workbook ) {
        Sheet sheet = workbook.createSheet(FIRST_SHEET);
        addTitleSheet(sheet, TITLE);
        generateHeader(sheet);
       // int headerRow = generateHeader(sheet);

    }
    private void createSecondSheet( Workbook workbook ) {
        Sheet sheet = workbook.createSheet(SECOND_SHEET);
        addTitleSheet(sheet, TITLE);
    }

    private void createThirdSheet( Workbook workbook ) {
        Sheet sheet = workbook.createSheet(THIRD_SHEET);
        addTitleSheet(sheet, TITLE);
    }

    private void addTitleSheet(Sheet sheet,String title) {
        try {
            int columnCount = 6; // Number of columns the title should span

            // Create the merged region for the title
            CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, columnCount - 1);
            sheet.addMergedRegion(mergedRegion);

            Row row = sheet.createRow(0); //create first row in sheet

            Cell cell = row.createCell(0);
            cell.setCellValue(title);

            //font with desired prop
            Font font = sheet.getWorkbook().createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 14);
            font.setBold(true);

            //font for cell
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text horizontally
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //Center the text vertically

            //borders
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            //background color
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(cellStyle);
        } catch (Exception e) {
            log.info(String.valueOf(e));
        }
    }

    private int generateHeader (Sheet sheet) {
        String[] headers = {"Partner", "InvoicePeriod"};
        String[] values = {"partnerName", "invoicePeriod"};

        int lastRow = sheet.getLastRowNum();

        Row headerRow = sheet.createRow(lastRow + 1);

        for (int i = 0; i < headers.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);

            Row valueRow = sheet.createRow(lastRow + 2 + i);
            Cell valueCell = valueRow.createCell(i);
            valueCell.setCellValue(values[i]);

        }

        return lastRow + 1;
    }

}
