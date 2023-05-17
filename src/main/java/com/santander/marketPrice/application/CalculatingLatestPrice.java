package com.santander.marketPrice.application;

import com.santander.marketPrice.domain.MessageConverter;
import com.santander.marketPrice.domain.Price;
import com.santander.marketPrice.domain.PriceList;
import com.santander.marketPrice.domain.Prices;

import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CalculatingLatestPrice {

    private static final Logger logger = Logger.getLogger(CalculatingLatestPrice.class.getName());

    private final MessageConverter messageConverter;
    private final Prices repository;

    public CalculatingLatestPrice(MessageConverter messageConverter, Prices repository) {
        this.messageConverter = messageConverter;
        this.repository = repository;
    }

    public void calculate(String message) {
        try {
            repository.deleteAll();
            Map<String, Price> groupedPrices = messageConverter.convert(message).getPrices()
                    .stream()
                    .map(Price::adjust)
                    .collect(Collectors.toMap(Price::instrumentName,
                            price -> price,
                            Price::getLatest));
            groupedPrices.values().forEach(repository::save);
        } catch (Exception e) {
            logger.info("Error in price calculation, error=" +  e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public PriceList getAll(){
        return repository.getAll();
    }
}
