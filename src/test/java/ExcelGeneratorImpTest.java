import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ServiceImp.ExcelGeneratorImp;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelGeneratorImpTest {

    @Test
    public void testGenerateExcelFile() throws IOException {
        ExcelGeneratorImp excelGeneratorImp = new ExcelGeneratorImp();

        excelGeneratorImp.generateExcelFile();
        String folderPath = System.getProperty("user.home") + "/Documents/";
        String filePath = folderPath + "traffic_report.xlsx";
        Assertions.assertNotNull(filePath, "File path should not be null");


        // Verify that the file contains three sheets
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            int numSheets = workbook.getNumberOfSheets();
            Assertions.assertEquals(3, numSheets, "Excel file should have three sheets");
        }
    }
}
