package com.santander.marketPrice.domain;

import java.util.List;

public class PriceList {

    List<Price> prices;

    public PriceList(List<Price> prices) {
        if (prices.size() == 0) {
            throw new IllegalArgumentException("Error: each message should have 1 or more lines in it");
        }
        this.prices = prices;
    }

    public List<Price> getPrices() {
        return prices;
    }

}
