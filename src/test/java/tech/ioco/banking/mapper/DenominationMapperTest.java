package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class DenominationMapperTest {

    @Autowired
    private DenominationMapper denominationMapper;

    @Test
    void findByDenominationTypeCode() {
        var denominations = denominationMapper.findByDenominationTypeCode("N");

        assertNotNull(denominations);
        assertEquals(5, denominations.size());
    }
}