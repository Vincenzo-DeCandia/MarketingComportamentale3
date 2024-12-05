package com.market.service;

import com.market.entity.Order;
import com.market.marketing.strategyanalysis.BehaviorAnalyzer;
import com.market.marketing.strategyanalysis.strategies.TfIdfAnalysisStrategy;
import com.market.repository.concreteRepository.OrderRepository;
import com.market.user.BaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UserAnalysisService class implements the IUserAnalysisService interface and provides functionality
 * for analyzing user behavior based on their orders.
 */
public class UserAnalysisService implements IUserAnalysisService {

    /**
     * Analyzes user behavior by analyzing their orders.
     *
     * @param listUsers The list of users whose behavior is to be analyzed.
     * @return A map where each key is a user and each value is the result of the behavior analysis
     *         (a string describing the user's behavior).
     */
    @Override
    public Map<BaseUser, String> analyzeUser(List<BaseUser> listUsers) {
        // Initialize a map to store orders for each user.
        Map<BaseUser, List<Order>> orderMap = new HashMap<>();

        // Create an instance of OrderRepository to retrieve user orders.
        OrderRepository orderRepository = new OrderRepository();

        // Loop through each user in the provided list and retrieve their orders.
        for (BaseUser baseUser : listUsers) {
            // Fetch orders for the current user and add to the orderMap.
            orderMap.put(baseUser, orderRepository.findByUserId(baseUser.getIdUser()));
        }

        // Create an instance of BehaviorAnalyzer with a TF-IDF analysis strategy.
        BehaviorAnalyzer behaviorAnalyzer = new BehaviorAnalyzer(new TfIdfAnalysisStrategy());


        // Perform the analysis on the orders and return the analysis result.
        return behaviorAnalyzer.analyzeBehavior(orderMap);
    }
}

