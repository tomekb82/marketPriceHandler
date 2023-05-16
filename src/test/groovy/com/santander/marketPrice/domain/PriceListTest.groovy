package com.santander.marketPrice.domain

import spock.lang.Specification

class PriceListTest extends Specification {

    def 'each message should have 1 or more lines in it' () {
        when:
            new PriceList(new ArrayList<Price>())
        then:
            thrown(IllegalArgumentException)
    }
}
