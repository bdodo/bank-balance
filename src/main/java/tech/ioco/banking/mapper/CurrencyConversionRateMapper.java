package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tech.ioco.banking.model.CurrencyConversionRate;

@Mapper
public interface CurrencyConversionRateMapper {
    @Select("select * from CURRENCY_CONVERSION_RATE where CURRENCY_CODE = #{currencyCode}")
    CurrencyConversionRate findByCurrencyCode(String currencyCode);
}
