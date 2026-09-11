package salesreporter;

import salesreporter.exception.InvalidCsvException;
import salesreporter.io.SalesDataReader;
import salesreporter.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalesDataReaderTest {

    @Test
    void readsValidCsvCorrectly(@TempDir Path tempDir) throws IOException, InvalidCsvException {
        Path csv = tempDir.resolve("test.csv");
        Files.writeString(csv,
                "product_id,product_name,category,quantity_sold,unit_price\n" +
                        "P001,Wireless Mouse,Electronics,12,25.50\n"
        );

        List<Product> products = new SalesDataReader().readProducts(csv.toString());

        assertEquals(1, products.size());
        assertEquals("Wireless Mouse", products.get(0).getProductName());
    }

    @Test
    void throwsExceptionWhenFileDoesNotExist() {
        SalesDataReader reader = new SalesDataReader();
        assertThrows(IOException.class, () -> reader.readProducts("nonexistent_file.csv"));
    }

    @Test
    void throwsInvalidCsvExceptionWhenRowHasMissingColumns(@TempDir Path tempDir) throws IOException {
        Path csv = tempDir.resolve("bad.csv");
        Files.writeString(csv,
                "product_id,product_name,category,quantity_sold,unit_price\n" +
                        "P001,Wireless Mouse,Electronics\n"
        );

        SalesDataReader reader = new SalesDataReader();
        assertThrows(InvalidCsvException.class, () -> reader.readProducts(csv.toString()));
    }

    @Test
    void skipsHeaderRowAutomatically(@TempDir Path tempDir) throws IOException, InvalidCsvException {
        Path csv = tempDir.resolve("test2.csv");
        Files.writeString(csv,
                "product_id,product_name,category,quantity_sold,unit_price\n" +
                        "P001,Mouse,Electronics,5,10.00\n" +
                        "P002,Pen,Stationery,20,0.50\n"
        );

        List<Product> products = new SalesDataReader().readProducts(csv.toString());

        assertEquals(2, products.size());
    }
}