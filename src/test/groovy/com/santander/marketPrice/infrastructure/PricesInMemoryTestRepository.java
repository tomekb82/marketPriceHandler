package com.santander.marketPrice.infrastructure;

import com.santander.marketPrice.domain.Price;
import com.santander.marketPrice.domain.PriceList;
import com.santander.marketPrice.domain.Prices;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PricesInMemoryTestRepository implements Prices {

    Map<String, Price> prices = new HashMap<>();

    @Override
    public void save(Price price) {
        prices.put(price.instrumentName().name(), price);
    }

    @Override
    public Optional<Price> getByInstrumentType(String type) {
        return Optional.ofNullable(prices.get(type));
    }

    @Override
    public PriceList getAll() {
        return new PriceList(prices.values().stream().toList());
    }
}
