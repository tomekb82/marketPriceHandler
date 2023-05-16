package com.santander.marketPrice.domain;

import java.util.Optional;

public interface Prices {

    void save(Price price);

    Optional<Price> getByInstrumentType(String type);

    PriceList getAll();
}
