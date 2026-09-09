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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Not enough arguments.");
            System.err.println("Usage: java -jar sales-reporter.jar <csv-file-path> <output-method> [output-file-path]");
            System.exit(1);
        }

        String csvFilePath = args[0];
        String outputMethod = args[1].toLowerCase().trim();

        if (!outputMethod.equals("console") && !outputMethod.equals("file")) {
            System.err.println("Error: output-method must be 'console' or 'file'.");
            System.exit(1);
        }

        String outputFilePath = null;
        if (outputMethod.equals("file")) {
            if (args.length < 3) {
                System.err.println("Error: output-file-path is required when output-method is 'file'.");
                System.exit(1);
            }
            outputFilePath = args[2];
        }

        try {
            List<Product> products = new SalesDataReader().readProducts(csvFilePath);
            SalesSummary summary = new SalesCalculator().calculate(products);
            String report = new ReportGenerator().generate(summary);

            OutputStrategy output = outputMethod.equals("console")
                    ? new ConsoleOutput()
                    : new FileOutput(outputFilePath);

            output.output(report);

        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (InvalidCsvException e) {
            System.err.println("Error: Invalid CSV data - " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error: Could not write output - " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }
    }
}