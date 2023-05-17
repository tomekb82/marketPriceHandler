package com.santander.marketPrice.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Price {
    PriceId priceId;
    InstrumentName instrumentName;
    Bid bid;
    Ask ask;
    FromDate from;

    public Price(PriceId priceId, InstrumentName instrumentName, Bid bid, Ask ask, FromDate from) {
        if (bid.sellPrice().compareTo(ask.buyPrice()) > 0) {
            throw new IllegalArgumentException("Sell price should be lower than buy price");
        }
        this.priceId = priceId;
        this.instrumentName = instrumentName;
        this.bid = bid;
        this.ask = ask;
        this.from = from;
    }

    public static Price fromMessage(String priceId, String instrumentName, String bid, String ask, String from) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
        return new Price(new PriceId(priceId.trim()),
                new InstrumentName(instrumentName.trim()),
                new Bid(new BigDecimal(bid.trim())),
                new Ask(new BigDecimal(ask.trim())),
                new FromDate(ZonedDateTime.parse(from.trim(), formatter.withZone(java.time.ZoneId.systemDefault()))));
    }

    public static String convertDate(FromDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
        return date.from().format(formatter.withZone(java.time.ZoneId.systemDefault()));
    }

    public Price getLatest(Price price){
        return from.from().isAfter(price.from.from()) ? new Price(priceId, instrumentName, bid, ask,from) : price;
    }

    public Price adjust() {
        return addCommission();
    }

    private Price addCommission() {
        double decreasePercentage = 0.1;
        Bid bidAdjusted = new Bid(bid.sellPrice().subtract(bid.sellPrice().multiply(BigDecimal.valueOf(decreasePercentage)).divide(new BigDecimal("100.0"))));
        Ask askAdjusted = new Ask(ask.buyPrice().add((ask.buyPrice().multiply(BigDecimal.valueOf(decreasePercentage)).divide(new BigDecimal("100.0")))));
        return new Price(priceId, instrumentName, bidAdjusted, askAdjusted, from);
    }

    public String priceId() {
        return priceId.id();
    }
    public String instrumentName() {
        return instrumentName.name();
    }

    public BigDecimal bid() {
        return bid.sellPrice();
    }
    public BigDecimal ask() {
        return ask.buyPrice();
    }
    public FromDate fromDate() {
        return from;
    }

    @Override
    public String toString() {
        return "Price{" +
                "priceId=" + priceId.id() +
                ", instrumentName=" + instrumentName.name() +
                ", bid=" + bid.sellPrice() +
                ", ask=" + ask.buyPrice() +
                ", from=" + from.from() +
                '}';
    }
}
