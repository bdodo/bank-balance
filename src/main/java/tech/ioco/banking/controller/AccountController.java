package tech.ioco.banking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tech.ioco.banking.model.dto.CurrencyAccountDto;
import tech.ioco.banking.model.dto.TransactionalAccountDto;
import tech.ioco.banking.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

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

}
