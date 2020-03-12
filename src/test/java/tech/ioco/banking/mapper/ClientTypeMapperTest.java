package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ioco.banking.model.ClientType;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
public class ClientTypeMapperTest {
    @Autowired
    private ClientTypeMapper clientTypeMapper;

    @Test
    void givenClientTypeCode_whenRecordsInDatabase_shouldReturnClientType() {
        ClientType clientType = clientTypeMapper.findByCode("I");

        assertAll(
                () -> assertNotNull(clientType),
                () -> assertEquals("Individual", clientType.getDescription())
        );
    }
}