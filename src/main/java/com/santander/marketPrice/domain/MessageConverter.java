package com.santander.marketPrice.domain;

import java.io.IOException;

public interface MessageConverter {
    PriceList convert(String message) throws IOException;
}
