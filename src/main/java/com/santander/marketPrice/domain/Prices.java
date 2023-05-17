package com.santander.marketPrice.domain;

public interface Prices {

    void save(Price price);

    PriceList getAll();
}
