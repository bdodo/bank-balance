package tech.ioco.banking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.ioco.banking.exception.AtmInsufficientCashException;
import tech.ioco.banking.exception.InsufficientFundsException;
import tech.ioco.banking.mapper.AtmAllocationMapper;
import tech.ioco.banking.mapper.ClientAccountMapper;
import tech.ioco.banking.model.ClientAccount;
import tech.ioco.banking.model.dto.AtmAllocationDto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawalService {
    private final ClientAccountMapper clientAccountMapper;
    private final AtmAllocationMapper atmAllocationMapper;

    public Map<Integer, Integer> withdrawFromAtm(int atmId, String accountNumber, BigDecimal withdrawalAmount){
        ClientAccount clientAccount = clientAccountMapper.findByAccountNumber(accountNumber);
        if(!isAmountAvailableForWithdrawal(withdrawalAmount, clientAccount)){
            log.error("The specified account does not have enough funds for the requested transaction");
            throw new InsufficientFundsException("Insufficient funds");
        }
        var maxAvailableCash = maxAtmCanDispense(atmId);
        if(withdrawalAmount.doubleValue() > maxAvailableCash){
            log.error("Amount {} not available, max available amount is: {}", withdrawalAmount, maxAvailableCash);
            throw new AtmInsufficientCashException(String.format("Amount not available, would you like to draw %f", maxAvailableCash));
        }

        var dispenseMap = canProvideBankNotesOnly(withdrawalAmount.doubleValue(), atmId);
        updateAccountBalance(clientAccount, withdrawalAmount);
        return dispenseMap;
    }

    public ClientAccount updateAccountBalance(ClientAccount clientAccount, BigDecimal withdrawalAmount) {
        log.info("Updating the withdrawal amounts in DB");
        clientAccount.setDisplayBalance(clientAccount.getDisplayBalance().subtract(withdrawalAmount));
        clientAccountMapper.updateAccountBalance(clientAccount);
        return clientAccountMapper.findByAccountNumber(clientAccount.getClientAccountNumber());
    }

    public boolean isAmountAvailableForWithdrawal(BigDecimal withdrawalAmount, ClientAccount clientAccount) {
        return AccountBalanceValidation.hasEnoughPositiveBalance()
                .or(AccountBalanceValidation.amountCanBeWithdrawnFromChequeAccount())
                .apply(clientAccount, withdrawalAmount);
    }

    public Map<Integer, Integer> canProvideBankNotesOnly(double withdrawalAmount, int atmId){
        log.info("checking if amount can be dispensed as notes and allocating the denominations");
        Map<Integer, Integer> notesToDispense = new HashMap<>();
        final double[] remainingAmount = {withdrawalAmount};
        atmAllocationMapper.findAllByAtmId(atmId)
                .stream()
                .map(AtmAllocationDto::new)
                .sorted( Comparator.comparingInt(AtmAllocationDto::getNoteValue).reversed())
                .forEach(allocationDto -> {
                    int count = (int) (remainingAmount[0] / allocationDto.getNoteValue());
                    if (count !=0){
                        count = Math.min(count, allocationDto.getNoteCount());
                        notesToDispense.put(allocationDto.getNoteValue(), count);
                        remainingAmount[0] -= allocationDto.getNoteValue()*count;
                    }
                });
        if(remainingAmount[0] == 0){
            return notesToDispense;
        }else {
            throw new AtmInsufficientCashException("The amount specified cannot be dispensed");
        }
    }

    public double maxAtmCanDispense(int atmId){
        return atmAllocationMapper.findAllByAtmId(atmId)
                .stream()
                .mapToDouble(allocation -> allocation.getDenomination().getValue().doubleValue() * (allocation.getCount()))
                .sum();
    }
}
