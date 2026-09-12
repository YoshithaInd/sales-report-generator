package salesreporter.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOutput implements OutputStrategy {
    private final String filePath;

    public FileOutput(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void output(String reportContent) throws IOException {
        Files.writeString(Paths.get(filePath), reportContent);
        System.out.println("Report successfully written to: " + filePath);
    }
}
