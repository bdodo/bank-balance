package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class DenominationTypeMapperTest {
    @Autowired
    private DenominationTypeMapper denominationTypeMapper;

    @Test
    void findByCode() {
        var denominationType = denominationTypeMapper.findByCode("C");
        assertNotNull(denominationType);
        assertEquals("Coin", denominationType.getDescription());
    }
}