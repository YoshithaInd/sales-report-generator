# Sales Report Generator

Command-line tool developed for **SENG 21222 – Software Construction (Assignment 1 – 2026)**, Faculty of Science, University of Kelaniya.

The tool reads product sales data from a CSV file, computes summary statistics (product revenue, category totals, best-selling product, highest revenue product, and grand total), and outputs a formatted report to either the console or a file.

---

## Team Members & Responsibilities

| Role | Member | GitHub Username | Key Contributions |
| :--- | :--- | :--- | :--- |
| **Member 1** | Core Logic | `@YoshithaInd` | Product model, sales calculation logic, summary data container |
| **Member 2** | File I/O & Testing | `@viduni003` | CSV file reader, output strategy implementation, unit testing |
| **Member 3** | Console & Documentation | `@thilini2003778` | CLI interface, argument validation, exception handling, report formatting, documentation |

---

## Architecture & Design Principles

The application applies object-oriented best practices and **SOLID** principles:

* **Single Responsibility Principle (SRP)**:
  * `SalesDataReader`: Dedicated solely to reading and parsing CSV input.
  * `SalesCalculator`: Dedicated solely to business logic and sales aggregations.
  * `ReportGenerator`: Dedicated solely to building and formatting the textual report.
  * `OutputStrategy`: Dedicated to destination dispatch (console, file, etc.).
  * `Main`: Dedicated to CLI argument parsing, workflow orchestration, and top-level exception handling.
* **Open-Closed Principle (OCP)**:
  * Uses the **Strategy Pattern** via `OutputStrategy`. New output targets (such as email, database, or HTML) can be added without modifying core report generation logic.

---

## Command-Line Usage

> ℹ️ **Note on Entry Point (`SalesReporter` vs `Main`)**:  
> The assignment guidelines specify `java SalesReporter <csv-file-path> <output-method> [output-file-path]`. In our Maven project structure, standard Java packaging conventions are followed by organizing classes under the `salesreporter` package, with the entry point implemented in `salesreporter.Main`. The `pom.xml` build configuration sets `salesreporter.Main` as the executable JAR entry point.

### Syntax

```bash
java salesreporter.Main <csv-file-path> <output-method> [output-file-path]
```
*(or using the packaged JAR)*:
```bash
java -jar target/sales-reporter.jar <csv-file-path> <output-method> [output-file-path]
```

### Arguments

| Argument | Requirement | Description |
| :--- | :--- | :--- |
| `<csv-file-path>` | Required | Path to the source CSV file containing sales records. |
| `<output-method>` | Required | Target output method: `'console'` or `'file'` (case-insensitive). |
| `[output-file-path]` | Conditional | Path to write the output file. **Required** when `<output-method>` is `'file'`. |

### Examples

**1. Outputting to Console:**
```bash
java salesreporter.Main data/sample_sales.csv console
```

**2. Outputting to File:**
```bash
java salesreporter.Main data/sample_sales.csv file output/report.txt
```

---

## Input CSV Format

The tool expects comma-separated records with the following columns (the header row is automatically skipped):

```csv
product_id, product_name, category, quantity_sold, unit_price
P001, Wireless Mouse, Electronics, 12, 25.50
P002, Notebook, Stationery, 35, 3.75
P003, USB Hub, Electronics, 8, 18.00
P004, Ballpoint Pen, Stationery, 100, 0.50
P005, HDMI Cable, Electronics, 20, 12.00
```

---

## Expected Output Format

```text
============================================
 PRODUCT SALES SUMMARY REPORT
============================================

--- Revenue Per Product ---
P001 Wireless Mouse       Electronics  $306.00
P002 Notebook             Stationery   $131.25
P003 USB Hub              Electronics  $144.00
P004 Ballpoint Pen        Stationery   $50.00
P005 HDMI Cable           Electronics  $240.00

--- Revenue Per Category ---
Electronics : $690.00
Stationery : $181.25

--- Highlights ---
Best-Selling Product : Ballpoint Pen (100 units)
Highest Revenue : Wireless Mouse ($306.00)
Grand Total Revenue : $871.25
============================================
```

---

## Error & Exception Handling

The application handles errors gracefully with meaningful user feedback and non-zero exit codes (`System.exit(1)`):

* **Missing Arguments**: Displays clear command usage if fewer than 2 arguments are provided.
* **Invalid Output Method**: Alerts if an unsupported output method is supplied (e.g., anything other than `console` or `file`).
* **Missing Output File Path**: Alerts if `file` is selected but no destination path is provided.
* **File Not Found**: Catches `FileNotFoundException` and displays an explicit error message when the input CSV cannot be located.
* **Invalid CSV Structure**: Custom `InvalidCsvException` is thrown and displayed when rows contain missing columns or malformed data.