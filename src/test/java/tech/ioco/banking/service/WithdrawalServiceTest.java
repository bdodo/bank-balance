package tech.ioco.banking.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ioco.banking.exception.AtmInsufficientCashException;
import tech.ioco.banking.exception.InsufficientFundsException;
import tech.ioco.banking.model.ClientAccount;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WithdrawalServiceTest {
    @Autowired
    private WithdrawalService withdrawalService;

    @Test
    void whenWithdrawFromAccount_thenReducesBalance() {
        var clientAccount = new ClientAccount();
        clientAccount.setDisplayBalance(BigDecimal.valueOf(10791.56));
        clientAccount.setClientAccountNumber("4055230225");
        var withdrawalAmount = BigDecimal.valueOf(120);
        var updatedAccount = withdrawalService.updateAccountBalance(clientAccount, withdrawalAmount);
        assertEquals(BigDecimal.valueOf(10671.56).setScale(3), updatedAccount.getDisplayBalance());
    }

    @Test
    void givenAmountAvailable_whenIsAmountAvailableForWithdrawal_thenReturnsTrue(){
        var clientAccount = new ClientAccount();
        clientAccount.setDisplayBalance(BigDecimal.valueOf(10791.56));
        clientAccount.setClientAccountNumber("4055230225");
        var withdrawalAmount = BigDecimal.valueOf(120);
        assertTrue(withdrawalService.isAmountAvailableForWithdrawal(withdrawalAmount, clientAccount));
    }

    @Test
    void canProvideBankNotesOnly(){
        var map = withdrawalService.canProvideBankNotesOnly(1450.0, 1);

        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(200));
        assertTrue(map.containsValue(2));
    }


    @Test
    void givenCannotProvideNotesOnly_thenThrowsException() {
        assertThrows(AtmInsufficientCashException.class, ()-> withdrawalService.canProvideBankNotesOnly(1453.0, 1));
    }

    @Test
    void maxAtmCanDispense(){
        double maxCash = withdrawalService.maxAtmCanDispense(1);
        assertEquals(1500.0, maxCash);
    }

    @Test
    void givenInsufficientFunds_whenWithdrawFromAtm_thenThrowsInsufficientFundsException(){
        assertThrows(InsufficientFundsException.class,() ->withdrawalService.withdrawFromAtm(1, "1018033450", BigDecimal.valueOf(1500.0)));
    }

    @Test
    void givenInsufficientCash_whenWithdrawFromAtm_thenThrowsAtmInsufficientCashException(){
        assertThrows(AtmInsufficientCashException.class,() ->withdrawalService.withdrawFromAtm(1, "4055230225", BigDecimal.valueOf(2500.0)));
    }

    @Test
    void givenSufficientFunds_whenWithdrawFromAtm_thenDispenseCash(){
        var map = withdrawalService.withdrawFromAtm(1, "4055230225",BigDecimal.valueOf(1450.0));

        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(200));
        assertTrue(map.containsValue(2));
    }
}