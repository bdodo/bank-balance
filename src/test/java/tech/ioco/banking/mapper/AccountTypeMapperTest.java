package tech.ioco.banking.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ioco.banking.model.AccountType;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class AccountTypeMapperTest {
    @Autowired
    private AccountTypeMapper accountTypeMapper;

    @Test
    void findByCode() {
        AccountType accountType = accountTypeMapper.findByCode("CHQ");

        assertAll(
                () -> assertNotNull(accountType),
                () -> assertEquals("Cheque Account", accountType.getDescription()),
                () -> assertTrue(accountType.isTransactional())
        );
    }
}