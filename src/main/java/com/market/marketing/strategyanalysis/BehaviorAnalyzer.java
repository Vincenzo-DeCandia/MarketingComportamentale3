package com.market.marketing.strategyanalysis;

import com.market.entity.Order;
import com.market.marketing.strategyanalysis.strategies.BehaviorAnalysisStrategy;
import com.market.user.BaseUser;

import java.util.List;
import java.util.Map;

/**
 * Context class in the Strategy Pattern for analyzing user behavior.
 * Delegates analysis to a {@link BehaviorAnalysisStrategy}.
 */
public class BehaviorAnalyzer {

    private BehaviorAnalysisStrategy strategy; // Current strategy for behavior analysis

    /**
     * Creates a {@code BehaviorAnalyzer} with the given strategy.
     *
     * @param strategy The behavior analysis strategy to use.
     */
    public BehaviorAnalyzer(BehaviorAnalysisStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Analyzes user behavior using the current strategy.
     *
     * @param listMap Map of users to their orders.
     * @return Map of users to their most relevant category.
     */
    public Map<BaseUser, String> analyzeBehavior(Map<BaseUser, List<Order>> listMap) {
        return strategy.analyze(listMap);
    }

    /**
     * Updates the strategy used for behavior analysis.
     *
     * @param strategy The new analysis strategy.
     */
    public void setStrategy(BehaviorAnalysisStrategy strategy) {
        this.strategy = strategy;
    }
}