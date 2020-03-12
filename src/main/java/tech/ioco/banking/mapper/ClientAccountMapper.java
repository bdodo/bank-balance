package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.*;
import tech.ioco.banking.model.ClientAccount;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ClientAccountMapper {

    @Select("select * from CLIENT_ACCOUNT where CLIENT_ID = #{clientId}")
    @Results({
            @Result(property = "accountType", column = "ACCOUNT_TYPE_CODE", one = @One(select = "tech.ioco.banking.mapper.AccountTypeMapper.findByCode")),
            @Result(property = "currency", column = "CURRENCY_CODE", one = @One(select = "tech.ioco.banking.mapper.CurrencyMapper.findByCode"))
    })
    List<ClientAccount> findAllByClientId(int clientId);

    @Select("select * from CLIENT_ACCOUNT where CLIENT_ACCOUNT_NUMBER = #{accountNumber}")
    @Results({
            @Result(property = "accountType", column = "ACCOUNT_TYPE_CODE", one = @One(select = "tech.ioco.banking.mapper.AccountTypeMapper.findByCode")),
            @Result(property = "currency", column = "CURRENCY_CODE", one = @One(select = "tech.ioco.banking.mapper.CurrencyMapper.findByCode"))
    })
    ClientAccount findByAccountNumber(String accountNumber);

    @Update("update CLIENT_ACCOUNT set DISPLAY_BALANCE = #{displayBalance} where CLIENT_ACCOUNT_NUMBER = #{clientAccountNumber}")
    void updateAccountBalance(ClientAccount account);
}
