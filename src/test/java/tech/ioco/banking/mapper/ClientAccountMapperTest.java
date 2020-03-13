package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ioco.banking.model.ClientAccount;

import java.math.BigDecimal;
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
                () -> assertNotNull(accounts.get(0).getAccountType()),
                () -> assertNotNull(accounts.get(1).getCurrency())
        );

    }

    @Test
    void findByAccountNumber() {
        ClientAccount account = clientAccountMapper.findByAccountNumber("1053664521");

        assertAll(
                () -> assertNotNull(account),
                () -> assertEquals("ZAR", account.getCurrency().getCurrencyCode()),
                () -> assertEquals("1053664521", account.getClientAccountNumber()),
                () -> assertEquals("SVGS", account.getAccountType().getAccountTypeCode())
        );
    }

    @Test
    void updateAccountBalance() {
        ClientAccount account = clientAccountMapper.findByAccountNumber("1053664521");
        account.setDisplayBalance(BigDecimal.valueOf(2000));

        clientAccountMapper.updateAccountBalance(account);

        var updatedAccount = clientAccountMapper.findByAccountNumber("1053664521");

        assertAll(
                () -> assertNotNull(updatedAccount),
                () -> assertEquals("ZAR", updatedAccount.getCurrency().getCurrencyCode()),
                () -> assertEquals("1053664521", updatedAccount.getClientAccountNumber()),
                () -> assertEquals("SVGS", updatedAccount.getAccountType().getAccountTypeCode()),
                () -> assertEquals(BigDecimal.valueOf(2000).setScale(3), updatedAccount.getDisplayBalance())
        );
    }
}