package salesreporter;

import salesreporter.output.ConsoleOutput;
import salesreporter.output.FileOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OutputStrategyTest {

    @Test
    void consoleOutputPrintsReportToStandardOut() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        new ConsoleOutput().output("Test report content");

        System.setOut(originalOut);
        assertTrue(outContent.toString().contains("Test report content"));
    }

    @Test
    void fileOutputWritesReportToDisk(@TempDir Path tempDir) throws IOException {
        Path outputFile = tempDir.resolve("report.txt");

        try {
            new FileOutput(outputFile.toString()).output("Test report content");
        } catch (Exception e) {
            fail("Should not throw: " + e.getMessage());
        }

        String content = Files.readString(outputFile);
        assertTrue(content.contains("Test report content"));
    }
}