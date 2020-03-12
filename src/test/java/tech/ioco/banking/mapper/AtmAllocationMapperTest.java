package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisTest
class AtmAllocationMapperTest {
    @Autowired
    private AtmAllocationMapper atmAllocationMapper;

    @Test
    void findAllByAtmId() {
        var atmAllocations = atmAllocationMapper.findAllByAtmId(1);

        assertNotNull(atmAllocations);
        assertEquals(5, atmAllocations.size());
    }
}