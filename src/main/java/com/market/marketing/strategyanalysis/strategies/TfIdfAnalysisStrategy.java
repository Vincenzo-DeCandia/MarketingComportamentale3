package com.market.marketing.strategyanalysis.strategies;

import com.market.entity.Order;
import com.market.entity.Product;
import com.market.user.BaseUser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Strategy implementation for analyzing user behavior based on the TF-IDF (Term Frequency-Inverse Document Frequency) model.
 * Categories are treated as terms, and users are treated as documents.
 */
public class TfIdfAnalysisStrategy implements BehaviorAnalysisStrategy {
    private Map<BaseUser, List<Order>> userOrders; // Stores orders for each user
    private final Map<String, Integer> occurencesCategories = new HashMap<>(); // Count of documents (users) containing each category
    private final Map<BaseUser, Map<String, Double>> tfUser = new HashMap<>(); // Term frequency (TF) for each category of every user
    private final Map<String, Double> idfCategories = new HashMap<>(); // Inverse document frequency (IDF) for each category
    private Map<BaseUser, Map<String, Double>> tfIdf; // TF-IDF values for each category for each user

    /**
     * Analyzes user behavior by identifying the most relevant category for each user based on their purchase history.
     *
     * @param userOrders A map of users to their corresponding list of orders.
     * @return A map of users to their most relevant category.
     */
    @Override
    public Map<BaseUser, String> analyze(Map<BaseUser, List<Order>> userOrders) {
        this.userOrders = userOrders;

        // Step 1: Initialize the Bag of Words (BoW) structure to count occurrences and calculate raw frequencies
        initBow();

        // Step 2: Calculate term frequency (TF) for each user
        calculateTf();

        // Step 3: Calculate inverse document frequency (IDF) for each category
        calculateIdf();

        // Step 4: Calculate TF-IDF values
        calculateTfIdf();

        // Step 5: Identify and return the best category for each user based on TF-IDF scores
        return findBestCategory();
    }

    /**
     * Calculates the total number of products purchased by summing quantities from the given orders.
     *
     * @param orders A list of orders for a specific user.
     * @return The total quantity of products purchased in the orders.
     */
    private int calculateTotalProducts(List<Order> orders) {
        int totalProducts = 0;
        if (orders != null) {
            for (Order order : orders) {
                List<Product> products = order.getProducts();
                for (Product product : products) {
                    totalProducts += product.getProductQuantity();
                }
            }
        }
        return totalProducts;
    }

    /**
     * Initializes the Bag of Words (BoW) by populating term frequencies (TF) for each user and
     * counting the number of users (documents) that contain each category.
     */
    private void initBow() {
        for (BaseUser user : userOrders.keySet()) {
            List<Order> orders = userOrders.get(user);
            Map<String, Double> tf = new HashMap<>();
            tfUser.put(user, tf);

            if (orders != null) {
                for (Order order : orders) {
                    // Iterate over products in the order
                    order.getProducts().forEach(product -> {
                        String category = product.getProductCategory();
                        tf.putIfAbsent(category, 0.0); // Initialize frequency to 0 if not present
                        if (product.getProductQuantity() > 0) {
                            tf.put(category, tf.get(category) + (double) product.getProductQuantity()); // Increment frequency
                        }
                    });
                }
            }
        }

        // Count the number of users (documents) containing each category
        for (Map.Entry<BaseUser, Map<String, Double>> entry : tfUser.entrySet()) {
            for (String category : entry.getValue().keySet()) {
                occurencesCategories.put(category, occurencesCategories.getOrDefault(category, 0) + 1);
            }
        }
    }

    /**
     * Calculates term frequency (TF) for each user by normalizing the raw term counts
     * by the total number of products purchased by the user.
     */
    private void calculateTf() {
        for (Map.Entry<BaseUser, Map<String, Double>> entry : tfUser.entrySet()) {
            Map<String, Double> tf = entry.getValue();
            int totalProducts = calculateTotalProducts(userOrders.get(entry.getKey()));
            if (totalProducts > 0) {
                tf.replaceAll((category, count) -> count / totalProducts); // Normalize by total products
            }
        }
    }

    /**
     * Calculates inverse document frequency (IDF) for each category based on the number of users (documents)
     * that contain the category.
     */
    private void calculateIdf() {
        int totalUsers = tfUser.size(); // Total number of users/documents
        for (Map.Entry<String, Integer> entry : occurencesCategories.entrySet()) {
            String category = entry.getKey();
            int docCount = entry.getValue();
            idfCategories.put(category, Math.log10((double) totalUsers / docCount)); // Compute IDF
        }
    }

    /**
     * Calculates the TF-IDF values for each category of each user by multiplying the term frequency (TF)
     * by the inverse document frequency (IDF).
     */
    private void calculateTfIdf() {
        tfIdf = new HashMap<>(tfUser); // Clone TF map to store TF-IDF values
        for (Map.Entry<BaseUser, Map<String, Double>> entry : tfIdf.entrySet()) {
            entry.getValue().replaceAll((category, tf) -> tf * idfCategories.get(category)); // Multiply TF by IDF
        }
    }

    /**
     * Identifies the category with the highest TF-IDF value for each user.
     *
     * @return A map of users to their most relevant category based on TF-IDF scores.
     */
    private Map<BaseUser, String> findBestCategory() {
        Map<BaseUser, String> bestCategories = new HashMap<>();
        for (Map.Entry<BaseUser, Map<String, Double>> entry : tfIdf.entrySet()) {
            // Filter categories with a TF-IDF value greater than 0
            Map<String, Double> filteredMap = entry.getValue().entrySet().stream()
                    .filter(e -> e.getValue() > 0)  // Keep only entries with value > 0
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            // If the filtered map is not empty, find the category with the maximum value
            if (filteredMap.isEmpty()) {
                bestCategories.put(entry.getKey(), "Sconosciuto"); // Default value for users without significant categories
            } else {
                String category = Collections.max(filteredMap.entrySet(), Map.Entry.comparingByValue()).getKey();
                bestCategories.put(entry.getKey(), category);
            }
        }
        return bestCategories;
    }
}

