package salesreporter.output;

public class ConsoleOutput implements OutputStrategy {
    @Override
    public void output(String reportContent) {
        System.out.print(reportContent);
    }
}