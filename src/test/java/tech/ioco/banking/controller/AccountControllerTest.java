package tech.ioco.banking.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.ioco.banking.exception.AtmInsufficientCashException;
import tech.ioco.banking.exception.ClientAccountException;
import tech.ioco.banking.exception.InsufficientFundsException;
import tech.ioco.banking.service.AccountService;
import tech.ioco.banking.service.WithdrawalService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.ioco.banking.utils.BankingConstants.NO_ACCOUNTS_MSG;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMybatis
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService mockAccountService;


    @MockBean
    private WithdrawalService mockWithdrawalService;

    @Test
    void getSortedTransactionalAccountsByClientId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/transactional")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("clientId", "1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void givenClientHasNoQualifyingAccounts_whenGetSortedTransactionalAccountsByClientId_theExpects404() throws Exception {
        when(mockAccountService.getSortedTransactionalAccountsByClientId(anyInt())).thenThrow(new ClientAccountException(NO_ACCOUNTS_MSG));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/account/transactional")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("clientId", "1"))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResolvedException().getMessage();
        assertTrue(actualResponseBody.equalsIgnoreCase(NO_ACCOUNTS_MSG));

    }

    @Test
    void getSortedCurrencyAccountsByClientId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("clientId", "1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void givenClientHasNoQualifyingAccounts_whenGetSortedCurrencyAccountsByClientId_theExpects404() throws Exception {
        when(mockAccountService.getSortedCurrencyAccountsByClientId(anyInt())).thenThrow(new ClientAccountException(NO_ACCOUNTS_MSG));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/account/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("clientId", "1"))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResolvedException().getMessage();
        assertTrue(actualResponseBody.equalsIgnoreCase(NO_ACCOUNTS_MSG));

    }

    @Test
    void withdrawSuccessfullyFromAtm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("atmId", "1")
                .param("accountNumber", "4055230225")
                .param("withdrawalAmount", "1000"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void givenClientHasInsufficientFunds_whenWithdrawing_theExpects400() throws Exception {
        when(mockWithdrawalService.withdrawFromAtm(anyInt(), anyString(), any())).thenThrow(new InsufficientFundsException(NO_ACCOUNTS_MSG));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("atmId", "1")
                .param("accountNumber", "4055230225")
                .param("withdrawalAmount", "1000"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResolvedException().getMessage();
        assertTrue(actualResponseBody.equalsIgnoreCase(NO_ACCOUNTS_MSG));
    }

    @Test
    void givenAtmHasInsufficientFunds_whenWithdrawing_theExpects500() throws Exception {
        when(mockWithdrawalService.withdrawFromAtm(anyInt(), anyString(), any())).thenThrow(new AtmInsufficientCashException(NO_ACCOUNTS_MSG));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("atmId", "1")
                .param("accountNumber", "4055230225")
                .param("withdrawalAmount", "1000"))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String actualResponseBody = mvcResult.getResolvedException().getMessage();
        assertTrue(actualResponseBody.equalsIgnoreCase(NO_ACCOUNTS_MSG));
    }

    @Test
    void givenAmountIncludesCoins_whenWithdrawing_theExpects500() throws Exception {
        when(mockWithdrawalService.withdrawFromAtm(anyInt(), anyString(), any())).thenThrow(new AtmInsufficientCashException(NO_ACCOUNTS_MSG));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("atmId", "1")
                .param("accountNumber", "4055230225")
                .param("withdrawalAmount", "1000"))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String actualResponseBody = mvcResult.getResolvedException().getMessage();
        assertTrue(actualResponseBody.equalsIgnoreCase(NO_ACCOUNTS_MSG));
    }

}