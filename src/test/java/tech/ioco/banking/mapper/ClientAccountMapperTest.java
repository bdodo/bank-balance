package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ioco.banking.model.ClientAccount;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class ClientAccountMapperTest {
    @Autowired
    private ClientAccountMapper clientAccountMapper;

    @Test
    void findAllByClientId() {
        List<ClientAccount> accounts = clientAccountMapper.findAllByClientId(1);

        assertAll(
                () -> assertEquals(8, accounts.size()),
                ()-> assertNotNull(accounts.get(0).getAccountType()),
                ()-> assertNotNull(accounts.get(1).getCurrency())
        );

    }

    @Test
    void findTransactionalAccountsByClientId() {
    }
}