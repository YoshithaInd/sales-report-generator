package salesreporter;

import salesreporter.exception.InvalidCsvException;
import salesreporter.io.SalesDataReader;
import salesreporter.model.Product;
import salesreporter.output.ConsoleOutput;
import salesreporter.output.FileOutput;
import salesreporter.output.OutputStrategy;
import salesreporter.report.ReportGenerator;
import salesreporter.service.SalesCalculator;
import salesreporter.service.SalesSummary;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // --- Validate arguments ---
        if (args.length < 2) {
            System.err.println("Error: missing arguments.");
            System.err.println("Usage: java Main <csv-file-path> <console|file> [output-file-path]");
            System.exit(1);
        }

        String csvFilePath = args[0];
        String outputMethod = args[1].toLowerCase();

        if (!outputMethod.equals("console") && !outputMethod.equals("file")) {
            System.err.println("Error: output-method must be 'console' or 'file'. Got: " + args[1]);
            System.exit(1);
        }

        if (outputMethod.equals("file") && args.length < 3) {
            System.err.println("Error: output-file-path is required when output-method is 'file'.");
            System.exit(1);
        }

        try {
            // --- Core logic (Member 1) ---
            List<Product> products = new SalesDataReader().readProducts(csvFilePath);
            SalesSummary summary = new SalesCalculator().calculate(products);
            String report = new ReportGenerator().generate(summary);

            // --- Output (Member 2's strategy classes) ---
            OutputStrategy output = outputMethod.equals("console")
                    ? new ConsoleOutput()
                    : new FileOutput(args[2]);

            output.output(report);

        } catch (InvalidCsvException e) {
            System.err.println("Error: invalid CSV data — " + e.getMessage());
            System.exit(1);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
}
