package tech.ioco.banking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.ioco.banking.exception.ClientAccountException;
import tech.ioco.banking.mapper.ClientAccountMapper;
import tech.ioco.banking.model.ClientAccount;
import tech.ioco.banking.model.Currency;
import tech.ioco.banking.model.dto.CurrencyAccountDto;
import tech.ioco.banking.model.dto.TransactionalAccountDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static tech.ioco.banking.utils.BankingConstants.NO_ACCOUNTS_MSG;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final ClientAccountMapper clientAccountMapper;

    public List<TransactionalAccountDto> getSortedTransactionalAccountsByClientId(int clientId) {
        log.info("getting transactional accounts for client with id: {}", clientId);
        var transactionalAccounts = clientAccountMapper.findAllByClientId(clientId)
                .stream()
                .filter(clientAccount -> clientAccount.getAccountType().isTransactional())
                .sorted(Comparator.comparing(ClientAccount::getDisplayBalance).reversed())
                .map(clientAccount -> TransactionalAccountDto.builder()
                        .accountNumber(clientAccount.getClientAccountNumber())
                        .accountType(clientAccount.getAccountType().getDescription())
                        .accountBalance(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP))
                        .build())
                .collect(Collectors.toList());

        if (transactionalAccounts.isEmpty()) {
            throw new ClientAccountException(NO_ACCOUNTS_MSG);
        } else {
            return transactionalAccounts;
        }
    }

    public List<CurrencyAccountDto> getSortedCurrencyAccountsByClientId(int clientId) {
        log.info("getting currency accounts for client with id: {}", clientId);
        var currencyAccounts = clientAccountMapper.findAllByClientId(clientId)
                .stream()
                .filter(clientAccount -> !clientAccount.getCurrency().getCurrencyCode().equalsIgnoreCase("ZAR"))
                .map(clientAccount -> CurrencyAccountDto.builder()
                        .accountNumber(clientAccount.getClientAccountNumber())
                        .currency(clientAccount.getCurrency().getCurrencyCode())
                        .currencyBalance(clientAccount.getDisplayBalance().setScale(clientAccount.getCurrency().getDecimalPlaces(), RoundingMode.HALF_UP))
                        .conversionRate(clientAccount.getCurrency().getCurrencyConversionRate().getRate())
                        .randAmount(calculateRandAmount(clientAccount.getDisplayBalance(), clientAccount.getCurrency()))
                        .build())
                .sorted(Comparator.comparing(CurrencyAccountDto::getRandAmount).reversed())
                .collect(Collectors.toList());

        if (currencyAccounts.isEmpty()) {
            throw new ClientAccountException(NO_ACCOUNTS_MSG);
        } else {
            return currencyAccounts;
        }
    }

    BigDecimal calculateRandAmount(BigDecimal currencyBalance, Currency currency){
        if (currency.getCurrencyConversionRate().getConversionIndicator().trim().equalsIgnoreCase("*")){
            return currencyBalance.multiply(currency.getCurrencyConversionRate().getRate()).setScale(currency.getDecimalPlaces(), RoundingMode.HALF_UP);
        }
        return currencyBalance.divide(currency.getCurrencyConversionRate().getRate(), currency.getDecimalPlaces(), RoundingMode.HALF_UP);
    }
}
