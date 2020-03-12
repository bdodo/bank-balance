package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ioco.banking.model.Atm;

import static org.junit.jupiter.api.Assertions.*;
@MybatisTest
class AtmMapperTest {
    @Autowired
    private AtmMapper atmMapper;

    @Test
    void findById() {
        Atm atm = atmMapper.findById(1);

        assertNotNull(atm);
        assertEquals("SANDTON1", atm.getName());
    }
}