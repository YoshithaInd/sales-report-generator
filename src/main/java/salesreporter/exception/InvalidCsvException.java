package salesreporter.exception;

/**
 * Custom checked exception thrown when an input CSV file has invalid format,
 * missing columns, or malformed data records.
 */
public class InvalidCsvException extends Exception {

    /**
     * Constructs a new InvalidCsvException with the specified detail message.
     *
     * @param message the detail error message explaining the CSV issue
     */
    public InvalidCsvException(String message) {
        super(message);
    }
}