package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ioco.banking.model.CurrencyConversionRate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class CurrencyConversionRateMapperTest {
    @Autowired
    private CurrencyConversionRateMapper currencyConversionRateMapper;

    @Test
    void findByCurrencyCode() {
        CurrencyConversionRate conversionRate = currencyConversionRateMapper.findByCurrencyCode("USD");
        assertAll(
                () -> assertNotNull(conversionRate),
                () -> assertEquals("*", conversionRate.getConversionIndicator()),
                () -> assertEquals(BigDecimal.valueOf(11.6167).setScale(8), conversionRate.getRate())
        );
    }
}