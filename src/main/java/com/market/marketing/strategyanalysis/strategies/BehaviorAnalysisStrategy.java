package com.market.marketing.strategyanalysis.strategies;

import com.market.entity.Order;
import com.market.user.BaseUser;

import java.util.List;
import java.util.Map;

/**
 * Interface defining the strategy for analyzing user behavior based on their purchase history.
 */
public interface BehaviorAnalysisStrategy {

    /**
     * Analyzes the behavior of users based on their past orders and identifies relevant information.
     *
     * @param userOrders A map where each key is a user and the value is the list of their orders.
     * @return A map where each key is a user and the value is the most relevant category for that user.
     */
    Map<BaseUser, String> analyze(Map<BaseUser, List<Order>> userOrders);
}