package com.santander.marketPrice.infrastructure;

import com.santander.marketPrice.application.CalculatingLatestPrice;
import com.santander.marketPrice.domain.Price;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.logging.Logger;

@Configuration
class MarketPriceConfiguration {

    private static final Logger logger = Logger.getLogger(MarketPriceConfiguration.class.getName());

    @Bean
    CalculatingLatestPrice calculatingLatestPrice() {
        return new CalculatingLatestPrice(new CsvMessageConverter(), new PricesInMemoryRepository());
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

    @Profile("local")
    @Bean
    CommandLineRunner init(CalculatingLatestPrice calculatingLatestPrice){
        return args -> {
            String message =
"""
106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100
110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110
""";
            calculatingLatestPrice.calculate(message);
            logger.info("Calculation of prizes completed");
            logger.info("Results:");
            logger.info(String.join("\n", calculatingLatestPrice.getAll().getPrices()
                    .stream()
                    .map(Object::toString)
                    .toList()));
        };
    }
}
