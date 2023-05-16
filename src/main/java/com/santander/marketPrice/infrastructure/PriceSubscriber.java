package com.santander.marketPrice.infrastructure;

import com.santander.marketPrice.application.CalculatingLatestPrice;
import com.santander.marketPrice.domain.MessageReader;

class PriceSubscriber implements MessageReader {

    private final CalculatingLatestPrice calculatingLatestPrice;

    public PriceSubscriber(CalculatingLatestPrice calculatingLatestPrice) {
        this.calculatingLatestPrice = calculatingLatestPrice;
    }

    @Override
    public void onMessage(String message) {
        calculatingLatestPrice.calculate(message);
    }

}
