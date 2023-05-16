package com.santander.marketPrice.infrastructure;

import com.santander.marketPrice.domain.MessageConverter;
import com.santander.marketPrice.domain.Price;
import com.santander.marketPrice.domain.PriceList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TestMessageConverter implements MessageConverter{

    @Override
    public PriceList convert(String message) throws IOException {
        List<Price> objects = new ArrayList<>();
        CSVParser csvParser = new CSVParser(new StringReader(message), CSVFormat.DEFAULT);
        for (CSVRecord record : csvParser) {
            objects.add(Price.fromMessage(record.get(0),
                    record.get(1), record.get(2), record.get(3), record.get(4)));
        }
        csvParser.close();
        return new PriceList(objects);
    }
}
