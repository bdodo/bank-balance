package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tech.ioco.banking.model.AccountType;

@Mapper
public interface AccountTypeMapper {
    @Select("select * from ACCOUNT_TYPE where ACCOUNT_TYPE_CODE = #{code}")
    AccountType findByCode(String code);
}
