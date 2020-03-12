package tech.ioco.banking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ioco.banking.model.dto.CurrencyAccountDto;
import tech.ioco.banking.model.dto.TransactionalAccountDto;
import tech.ioco.banking.service.AccountService;
import tech.ioco.banking.service.WithdrawalService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;
    private final WithdrawalService withdrawalService;

    @GetMapping("transactional")
    public List<TransactionalAccountDto> getTransactionalAccountsByClientId(@RequestParam int clientId){
        log.info("request to retrieve transactional accounts for client with id: {}", clientId);
        return accountService.getSortedTransactionalAccountsByClientId(clientId);
    }

    @GetMapping("currency")
    public List<CurrencyAccountDto> getCurrencyAccountsByClientId(@RequestParam int clientId){
        log.info("request to retrieve currency accounts for client with id: {}", clientId);
        return accountService.getSortedCurrencyAccountsByClientId(clientId);
    }

    @GetMapping("withdraw")
    public ResponseEntity<Map<Integer, Integer>> withdrawFromAtm(@RequestParam int atmId,
                                               @RequestParam String accountNumber,
                                               @RequestParam BigDecimal withdrawalAmount){
        log.info("request to withdraw R{} from account number: {}", withdrawalAmount, accountNumber);
        return ResponseEntity.ok().body(withdrawalService.withdrawFromAtm(atmId, accountNumber, withdrawalAmount));
    }
}
