package com.market.repository;

import com.market.user.concreteuser.User;

import java.util.List;

public interface IUserAnalyzeRepository {
    List<User> searchByOrderNumber(int orderNumber);
    List<User> randomUser(int count);
}
