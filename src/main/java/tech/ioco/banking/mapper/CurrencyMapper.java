package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tech.ioco.banking.model.Currency;

@Mapper
public interface CurrencyMapper {
    @Select("select * from CURRENCY where CURRENCY_CODE = #{code}")
    Currency findByCode(String code);
}
