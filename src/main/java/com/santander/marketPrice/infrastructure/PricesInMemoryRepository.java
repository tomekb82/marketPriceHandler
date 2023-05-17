package com.santander.marketPrice.infrastructure;

import com.santander.marketPrice.domain.Price;
import com.santander.marketPrice.domain.PriceList;
import com.santander.marketPrice.domain.Prices;

import java.util.HashMap;
import java.util.Map;

class PricesInMemoryRepository implements Prices {

    Map<String, Price> prices = new HashMap<>();

    @Override
    public void save(Price price) {
        prices.put(price.instrumentName(), price);
    }

    @Override
    public PriceList getAll() {
        return new PriceList(prices.values().stream().toList());
    }
}
