package com.market.marketing.patternobserver.observer;

import com.market.entity.Offer;

public interface IObserver {
    void update(Offer offer) throws Exception;
}
