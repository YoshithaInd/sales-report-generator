//member 1

package salesreporter.service;

import salesreporter.model.Product;
import java.util.*;

public class SalesCalculator {

    public SalesSummary calculate(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be empty.");
        }

        Map<Product, Double> revenuePerProduct = new LinkedHashMap<>();
        Map<String, Double> revenuePerCategory = new LinkedHashMap<>();

        double grandTotal = 0.0;
        Product bestSeller = products.get(0);
        Product highestRevenue = products.get(0);

        for (Product p : products) {
            double revenue = p.getRevenue();
            revenuePerProduct.put(p, revenue);

            revenuePerCategory.merge(p.getCategory(), revenue, Double::sum);

            grandTotal += revenue;

            if (p.getQuantitySold() > bestSeller.getQuantitySold()) {
                bestSeller = p;
            }
            if (revenue > highestRevenue.getRevenue()) {
                highestRevenue = p;
            }
        }

        return new SalesSummary(revenuePerProduct, revenuePerCategory,
                bestSeller, highestRevenue, grandTotal);
    }
}