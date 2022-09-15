package guru.qa.filesParsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipFile;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class ZipFileParseTest {

    ClassLoader classLoader = ZipFileParseTest.class.getClassLoader();

    @Test
    void pdfTest() throws Exception {
        InputStream file = getFile("zip_files.zip", "File_pdf.pdf");
        PDF pdf = new PDF(file);
        assertThat(pdf.text).contains("Сидоров Олег 03.03.2023");
        closeInputStream(file);
    }

    @Test
    void xlsTest() throws Exception {
        InputStream file = getFile("zip_files.zip", "File_xls.xlsx");
        XLS xls = new XLS(file);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(0)
                .getCell(1)
                .getStringCellValue()
        ).contains("Сергей");
        closeInputStream(file);
    }

    @Test
    void csvTest() throws Exception {
        InputStream file = getFile("zip_files.zip", "File_csv.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(file, UTF_8));
        List<String[]> csv = csvReader.readAll();
        assertThat(csv).contains(
                new String[] {"Урок 1","Алгебра","19.09.2022"},
                new String[] {"Урок 2","Геометрия","19.09.2022"},
                new String[] {"Урок 3","Физика","19.09.2022"},
                new String[] {"Урок 4","Химия","19.09.2022"}
        );
        closeInputStream(file);
    }

    private InputStream getFile(String archiveName, String fileName) throws Exception {
        URL zipUrl = classLoader.getResource("zip_files.zip");
        File zipFile = new File(zipUrl.toURI());
        ZipFile zip = new ZipFile(zipFile);
        return zip.getInputStream(zip.getEntry(fileName));
    }

    private void closeInputStream (InputStream file) throws IOException {
        if(file !=null)
            file.close();
    }
}
