package com.santander.marketPrice.application

import com.santander.Fixture
import com.santander.marketPrice.application.CalculatingLatestPrice
import com.santander.marketPrice.domain.MessageConverter
import com.santander.marketPrice.domain.MessageReader
import com.santander.marketPrice.infrastructure.CsvMessageConverter
import com.santander.marketPrice.infrastructure.PriceSubscriber
import com.santander.marketPrice.infrastructure.PricesInMemoryRepository
import com.santander.marketPrice.infrastructure.PricesInMemoryTestRepository
import com.santander.marketPrice.infrastructure.TestMessageConverter
import spock.lang.Specification

class CalculatingLatestPriceTest extends Specification{

    def 'process message'() {
        given:
            var message = new Fixture().readCSV("prices.csv")
            var repository = new PricesInMemoryTestRepository()
            CalculatingLatestPrice calculatingLatestPrice = new CalculatingLatestPrice(new TestMessageConverter(), repository)
        when:
            calculatingLatestPrice.calculate(message)
        then:
            var result = repository.getAll()
            result.prices.size() == 3
            result.prices.get(0).priceId().id() == "109"
            result.prices.get(0).instrumentName().name() == "GBP/USD"
            result.prices.get(1).priceId().id() == "110"
            result.prices.get(1).instrumentName().name() == "EUR/JPY"
            result.prices.get(2).priceId().id() == "106"
            result.prices.get(2).instrumentName().name() == "EUR/USD"
    }

    def 'throw error when wrong input data'() {
        given:
            var message = new Fixture().readCSV("wrong-prices.csv")
            var repository = new PricesInMemoryTestRepository()
            CalculatingLatestPrice calculatingLatestPrice = new CalculatingLatestPrice(new TestMessageConverter(), repository)
        when:
            calculatingLatestPrice.calculate(message)
        then:
            thrown(IllegalStateException)
    }
}
