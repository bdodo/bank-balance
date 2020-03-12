package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ioco.banking.model.Currency;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class CurrencyMapperTest {
    @Autowired
    private CurrencyMapper currencyMapper;

    @Test
    void findByCode() {
        Currency currency = currencyMapper.findByCode("ZAR");

        assertAll(
                () -> assertNotNull(currency),
                () -> assertEquals("ZAR", currency.getCurrencyCode()),
                () -> assertEquals("South African rand", currency.getDescription()),
                () -> assertEquals(2, currency.getDecimalPlaces())
        );
    }
}