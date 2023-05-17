package com.santander.marketPrice.application;

import com.santander.marketPrice.domain.Price;
import com.santander.marketPrice.domain.PriceList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class PriceController {

    CalculatingLatestPrice calculatingLatestPrice;

    public PriceController(CalculatingLatestPrice calculatingLatestPrice) {
        this.calculatingLatestPrice = calculatingLatestPrice;
    }

    @GetMapping("/prices/latest")
    ResponseEntity<PriceListDTO> getLatestPrices(){
            return ok(new PriceListDTO(calculatingLatestPrice.getAll()));
    }

    class PriceDto {
        String priceId;
        String instrumentName;
        BigDecimal bid;
        BigDecimal ask;
        String from;

        public PriceDto(Price price) {
            this.priceId = price.priceId();
            this.instrumentName = price.instrumentName();
            this.bid = price.bid();
            this.ask = price.ask();
            this.from = Price.convertDate(price.fromDate());
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getInstrumentName() {
            return instrumentName;
        }

        public void setInstrumentName(String instrumentName) {
            this.instrumentName = instrumentName;
        }

        public BigDecimal getBid() {
            return bid;
        }

        public void setBid(BigDecimal bid) {
            this.bid = bid;
        }

        public BigDecimal getAsk() {
            return ask;
        }

        public void setAsk(BigDecimal ask) {
            this.ask = ask;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

    class PriceListDTO {

        List<PriceDto> prices;

        public PriceListDTO(PriceList priceList){
            this.prices = priceList.getPrices().stream().map(PriceDto::new).toList();
        }

        public List<PriceDto> getPrices() {
            return prices;
        }

        public void setPrices(List<PriceDto> prices) {
            this.prices = prices;
        }
    }

}
