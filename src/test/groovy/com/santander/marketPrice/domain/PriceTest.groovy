package com.santander.marketPrice.domain

import spock.lang.Specification

import java.time.ZonedDateTime

class PriceTest extends Specification {

    def 'sell price should be lower than buy price' () {
        given:
            PriceId priceId = new PriceId("123")
            InstrumentName instrumentName = new InstrumentName("EUR")
            Bid bid = new Bid(BigDecimal.TEN)
            Ask ask = new Ask(BigDecimal.ONE)
            FromDate from = new FromDate(ZonedDateTime.now())
        when:
            new Price(priceId, instrumentName, bid, ask, from)
        then:
            thrown(IllegalArgumentException)
    }

    def 'should get latest price' () {
        given:
            PriceId priceId = new PriceId("123")
            InstrumentName instrumentName = new InstrumentName("EUR")
            Bid bid = new Bid(BigDecimal.ONE)
            Ask ask = new Ask(BigDecimal.TEN)
            FromDate from = new FromDate(ZonedDateTime.now())
            FromDate fromLatest = new FromDate(ZonedDateTime.now().plusDays(2))
            Price price = new Price(priceId, instrumentName, bid, ask, from)
            Price latest = new Price(priceId, instrumentName, bid, ask, fromLatest)
        when:
            Price result = price.getLatest(latest)
        then:
            result == latest
    }

    def 'should adjust price' () {
        given:
            PriceId priceId = new PriceId("123")
            InstrumentName instrumentName = new InstrumentName("EUR")
            Bid bid = new Bid(BigDecimal.ONE)
            Ask ask = new Ask(BigDecimal.TEN)
            FromDate from = new FromDate(ZonedDateTime.now())
            Price price = new Price(priceId, instrumentName, bid, ask, from)
        when:
            Price result = price.adjust()
        then:
            result.bid() == bid.sellPrice().subtract(bid.sellPrice().multiply(BigDecimal.valueOf(0.1)).divide(new BigDecimal("100.0")))
            result.ask() == ask.buyPrice().add(ask.buyPrice().multiply(BigDecimal.valueOf(0.1)).divide(new BigDecimal("100.0")))
    }
}
