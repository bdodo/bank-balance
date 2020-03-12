package tech.ioco.banking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ioco.banking.model.dto.TransactionalAccountDto;
import tech.ioco.banking.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("transactional/client/{id}")
    public List<TransactionalAccountDto> getTransactionalAccountsByClientId(@PathVariable(name = "id") int clientId){
        log.info("request to retrieve transactional accounts for client with id: {}", clientId);
        return accountService.getSortedTransactionalAccountsByClientId(clientId);
    }

}
