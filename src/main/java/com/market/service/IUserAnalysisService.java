package com.market.service;

import com.market.user.BaseUser;


import java.util.List;
import java.util.Map;

import java.util.List;
import java.util.Map;

/**
 * Interface for analyzing a list of users.
 * Provides a method to analyze a list of BaseUser objects and return a map with analysis results.
 */
public interface IUserAnalysisService {

    /**
     * Analyzes a list of users and returns a map where each user is associated with a string representing
     * the result of the analysis.
     *
     * @param listUsers The list of users to be analyzed.
     * @return A map where the key is a user (BaseUser) and the value is a string containing the result of the analysis.
     */
    public Map<BaseUser, String> analyzeUser(List<BaseUser> listUsers);
}
