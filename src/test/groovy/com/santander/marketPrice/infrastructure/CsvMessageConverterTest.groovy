package com.santander.marketPrice.infrastructure

import com.santander.marketPrice.domain.Price
import com.santander.marketPrice.domain.PriceList
import spock.lang.Specification

class CsvMessageConverterTest extends Specification {

    def 'should convert csv message'() {
        given:
            var message =
"""
106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100
110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110
""";
            CsvMessageConverter messageConverter = new CsvMessageConverter()
        when:
            PriceList result = messageConverter.convert(message)
        then:
            result.prices.size() == 5
            result.prices.get(0).priceId().id() == "106"
            result.prices.get(0).instrumentName().name() == "EUR/USD"
            result.prices.get(0).bid().sellPrice() == new BigDecimal("1.1000")
            result.prices.get(0).ask().buyPrice()== new BigDecimal("1.2000")
            Price.convertDate(result.prices.get(0).fromDate()) == "01-06-2020 12:01:01:001"


    }
}
