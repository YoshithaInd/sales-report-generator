//member 1

package salesreporter.service;

import salesreporter.model.Product;
import java.util.*;

public class SalesSummary {
    private final Map<Product, Double> revenuePerProduct;
    private final Map<String, Double> revenuePerCategory;
    private final Product bestSellingProduct;
    private final Product highestRevenueProduct;
    private final double grandTotalRevenue;

    public SalesSummary(Map<Product, Double> revenuePerProduct,
                        Map<String, Double> revenuePerCategory,
                        Product bestSellingProduct,
                        Product highestRevenueProduct,
                        double grandTotalRevenue) {
        this.revenuePerProduct = revenuePerProduct;
        this.revenuePerCategory = revenuePerCategory;
        this.bestSellingProduct = bestSellingProduct;
        this.highestRevenueProduct = highestRevenueProduct;
        this.grandTotalRevenue = grandTotalRevenue;
    }

    public Map<Product, Double> getRevenuePerProduct() { return revenuePerProduct; }
    public Map<String, Double> getRevenuePerCategory() { return revenuePerCategory; }
    public Product getBestSellingProduct() { return bestSellingProduct; }
    public Product getHighestRevenueProduct() { return highestRevenueProduct; }
    public double getGrandTotalRevenue() { return grandTotalRevenue; }
}