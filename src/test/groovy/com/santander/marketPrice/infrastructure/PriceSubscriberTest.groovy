package com.santander.marketPrice.infrastructure

import com.santander.Fixture
import com.santander.marketPrice.application.CalculatingLatestPrice
import com.santander.marketPrice.infrastructure.PricesInMemoryTestRepository
import com.santander.marketPrice.infrastructure.TestMessageConverter
import spock.lang.Specification

class PriceSubscriberTest extends Specification{

    def 'process message'() {
        given:
            var message = new Fixture().readCSV("prices.csv")
            var repository = new PricesInMemoryTestRepository()
            CalculatingLatestPrice calculatingLatestPrice = new CalculatingLatestPrice(new TestMessageConverter(), repository)
            PriceSubscriber priceSubscriber = new PriceSubscriber(calculatingLatestPrice)
        when:
            priceSubscriber.onMessage(message)
        then:
            var result = repository.getAll()
            result.prices.size() == 3
            result.prices.get(0).priceId() == "109"
            result.prices.get(0).instrumentName() == "GBP/USD"
            result.prices.get(1).priceId() == "110"
            result.prices.get(1).instrumentName() == "EUR/JPY"
            result.prices.get(2).priceId() == "106"
            result.prices.get(2).instrumentName() == "EUR/USD"
    }

    def 'throw error when wrong input data'() {
        given:
            var message = new Fixture().readCSV("wrong-prices.csv")
            var repository = new PricesInMemoryTestRepository()
            CalculatingLatestPrice calculatingLatestPrice = new CalculatingLatestPrice(new TestMessageConverter(), repository)
            PriceSubscriber priceSubscriber = new PriceSubscriber(calculatingLatestPrice)
        when:
            priceSubscriber.onMessage(message)
        then:
            thrown(IllegalStateException)
    }
}
