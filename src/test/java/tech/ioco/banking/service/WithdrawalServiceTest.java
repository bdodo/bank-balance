package tech.ioco.banking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tech.ioco.banking.exception.AtmInsufficientCashException;
import tech.ioco.banking.exception.InsufficientFundsException;
import tech.ioco.banking.mapper.AtmAllocationMapper;
import tech.ioco.banking.mapper.ClientAccountMapper;
import tech.ioco.banking.model.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = WithdrawalService.class)
class WithdrawalServiceTest {
    @Autowired
    private WithdrawalService withdrawalService;

    @MockBean
    private ClientAccountMapper clientAccountMapper;

    @MockBean
    private AtmAllocationMapper atmAllocationMapper;

    private ClientAccount mockAccount1, mockAccount2, mockAccount3, mockAccount4;

    @BeforeEach
    void setUp() {
        var mockAccountType = new AccountType("CHQ", "Cheque Account", true);
        var mockAccountType2 = new AccountType("LN", "Loan Account", false);
        var mockCurrency = new Currency("R", "Rand", 2, new CurrencyConversionRate(null, "*", BigDecimal.valueOf(0.8)));
        mockAccount1 = new ClientAccount(1, "12345", mockAccountType, mockCurrency, BigDecimal.valueOf(2000));
        mockAccount2 = new ClientAccount(2, "12346", mockAccountType, mockCurrency, BigDecimal.valueOf(100));
        mockAccount3 = new ClientAccount(3, "12347", mockAccountType2, mockCurrency, BigDecimal.valueOf(3000));
        mockAccount4 = new ClientAccount(4, "12348", mockAccountType, mockCurrency, BigDecimal.valueOf(1760));

        List<ClientAccount> clientAccounts = List.of(mockAccount1, mockAccount2, mockAccount3, mockAccount4);
        when(clientAccountMapper.findAllByClientId(anyInt())).thenReturn(clientAccounts);

        var noteDenomination = new DenominationType('N', "Note");
        var ten = new Denomination(1, BigDecimal.TEN, noteDenomination);
        var twenty = new Denomination(2, BigDecimal.valueOf(20), noteDenomination);
        var fifty = new Denomination(3, BigDecimal.valueOf(50), noteDenomination);
        var hundred = new Denomination(4, BigDecimal.valueOf(100), noteDenomination);
        var twoHundred = new Denomination(5, BigDecimal.valueOf(200), noteDenomination);

        var mockAtm = new Atm(1, "ATM1","1DP");
        var mockAllocation1 = new AtmAllocation(1, mockAtm, ten, 50);
        var mockAllocation2 = new AtmAllocation(2, mockAtm, twenty, 5);
        var mockAllocation3 = new AtmAllocation(3, mockAtm, fifty, 4);
        var mockAllocation4 = new AtmAllocation(4, mockAtm, hundred, 3);
        var mockAllocation5 = new AtmAllocation(5, mockAtm, twoHundred, 2);

        var atmAllocations = List.of(mockAllocation1, mockAllocation2, mockAllocation3, mockAllocation4,mockAllocation5);
        when(atmAllocationMapper.findAllByAtmId(anyInt())).thenReturn(atmAllocations);
    }

    @Test
    void whenWithdrawFromAccount_thenReducesBalance() {
        when(clientAccountMapper.findByAccountNumber(anyString())).thenReturn(mockAccount4);
        var withdrawalAmount = BigDecimal.valueOf(120);
        var updatedAccount = withdrawalService.updateAccountBalance(mockAccount1, withdrawalAmount);
        assertEquals(BigDecimal.valueOf(1760), updatedAccount.getDisplayBalance());
    }

    @Test
    void givenAmountAvailable_whenIsAmountAvailableForWithdrawal_thenReturnsTrue(){
        var withdrawalAmount = BigDecimal.valueOf(120);
        assertTrue(withdrawalService.isAmountAvailableForWithdrawal(withdrawalAmount, mockAccount1));
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
        when(clientAccountMapper.findByAccountNumber(anyString())).thenReturn(mockAccount2);
        assertThrows(InsufficientFundsException.class,() ->withdrawalService.withdrawFromAtm(1, "1018033450", BigDecimal.valueOf(10500.0)));
    }

    @Test
    void givenInsufficientCash_whenWithdrawFromAtm_thenThrowsAtmInsufficientCashException(){
        when(clientAccountMapper.findByAccountNumber(anyString())).thenReturn(mockAccount1);
        assertThrows(AtmInsufficientCashException.class,() ->withdrawalService.withdrawFromAtm(1, "4055230225", BigDecimal.valueOf(2500.0)));
    }

    @Test
    void givenSufficientFunds_whenWithdrawFromAtm_thenDispenseCash(){
        when(clientAccountMapper.findByAccountNumber(anyString())).thenReturn(mockAccount1);
        var map = withdrawalService.withdrawFromAtm(1, "4055230225",BigDecimal.valueOf(1450.0));

        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(200));
        assertTrue(map.containsValue(2));
    }
}