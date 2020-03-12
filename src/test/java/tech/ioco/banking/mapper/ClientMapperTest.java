package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ioco.banking.model.Client;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class ClientMapperTest {
    @Autowired
    ClientMapper clientMapper;

    @Test
    void givenClientId_whenClientExistInDB_thenReturnsClient() {
        Client client = clientMapper.findById(1);

        assertAll(
                () -> assertNotNull(client),
                () -> assertEquals("Ms", client.getTitle()),
                () -> assertEquals("Marylou", client.getName()),
                () -> assertEquals("Melcher", client.getSurname())
        );
    }
}