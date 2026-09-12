package salesreporter.report;

import salesreporter.model.Product;
import salesreporter.service.SalesSummary;
import java.util.Map;

/**
 * Responsible for formatting sales summary data into the required
 * human-readable ASCII summary report.
 * <p>
 * Adheres to the Single Responsibility Principle (SRP) by handling
 * presentation and tabular layout exclusively.
 */
public class ReportGenerator {

    /**
     * Generates a formatted text report from the computed sales summary.
     *
     * @param summary the aggregated sales statistics
     * @return a formatted string containing revenue per product, category totals, and highlights
     */
    public String generate(SalesSummary summary) {
        StringBuilder sb = new StringBuilder();

        sb.append("============================================\n");
        sb.append(" PRODUCT SALES SUMMARY REPORT\n");
        sb.append("============================================\n\n");

        sb.append("--- Revenue Per Product ---\n");
        for (Map.Entry<Product, Double> entry : summary.getRevenuePerProduct().entrySet()) {
            Product p = entry.getKey();
            sb.append(String.format("%s %-20s %-12s $%.2f%n",
                    p.getProductId(), p.getProductName(), p.getCategory(), entry.getValue()));
        }

        sb.append("\n--- Revenue Per Category ---\n");
        for (Map.Entry<String, Double> entry : summary.getRevenuePerCategory().entrySet()) {
            sb.append(String.format("%s : $%.2f%n", entry.getKey(), entry.getValue()));
        }

        sb.append("\n--- Highlights ---\n");
        sb.append(String.format("Best-Selling Product : %s (%d units)%n",
                summary.getBestSellingProduct().getProductName(),
                summary.getBestSellingProduct().getQuantitySold()));
        sb.append(String.format("Highest Revenue : %s ($%.2f)%n",
                summary.getHighestRevenueProduct().getProductName(),
                summary.getHighestRevenueProduct().getRevenue()));
        sb.append(String.format("Grand Total Revenue : $%.2f%n", summary.getGrandTotalRevenue()));

        sb.append("============================================\n");

        return sb.toString();
    }
}
