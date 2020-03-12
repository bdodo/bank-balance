package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ioco.banking.model.ClientSubType;
import tech.ioco.banking.model.ClientType;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class ClientSubTypeMapperTest {
    @Autowired
    ClientSubTypeMapper clientSubTypeMapper;

    @Test
    void givenClientSubTypeCode_whenRecordExistInDatabase_shouldReturnClientSubType() {
        var mockType = new ClientType("I", "Individual");
        ClientSubType clientSubType = clientSubTypeMapper.findByCode("FEM");

        assertAll(
                () -> assertNotNull(clientSubType),
                () -> assertEquals("Female", clientSubType.getDescription()),
                () -> assertEquals(mockType, clientSubType.getClientType())
        );
    }
}