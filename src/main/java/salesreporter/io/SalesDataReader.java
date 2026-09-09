//member 1

package salesreporter.io;

import salesreporter.model.Product;
import salesreporter.exception.InvalidCsvException;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SalesDataReader {

    public List<Product> readProducts(String filePath) throws IOException, InvalidCsvException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("CSV file not found: " + filePath);
        }

        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            boolean firstLine = true;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;

                if (firstLine) {
                    firstLine = false;
                    if (line.toLowerCase().contains("product_id")) {
                        continue;
                    }
                }

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    throw new InvalidCsvException(
                            "Row " + lineNumber + " has missing columns: " + line);
                }

                try {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String category = parts[2].trim();
                    int qty = Integer.parseInt(parts[3].trim());
                    double price = Double.parseDouble(parts[4].trim());

                    products.add(new Product(id, name, category, qty, price));
                } catch (NumberFormatException e) {
                    throw new InvalidCsvException(
                            "Row " + lineNumber + " has invalid numeric data: " + line);
                }
            }
        }

        if (products.isEmpty()) {
            throw new InvalidCsvException("CSV file contains no product data.");
        }

        return products;
    }
}
