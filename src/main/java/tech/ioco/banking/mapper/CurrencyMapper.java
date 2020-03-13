package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.*;
import tech.ioco.banking.model.Currency;

@Mapper
public interface CurrencyMapper {
    @Select("select * from CURRENCY where CURRENCY_CODE = #{code}")
    @Results({
            @Result(id = true, property = "currencyCode", column = "CURRENCY_CODE"),
            @Result(property = "currencyConversionRate", column = "CURRENCY_CODE", one = @One(select = "tech.ioco.banking.mapper.CurrencyConversionRateMapper.findByCurrencyCode"))
    })

    Currency findByCode(String code);
}
