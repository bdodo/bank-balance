package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tech.ioco.banking.model.Atm;

@Mapper
public interface AtmMapper {
    @Select("select * from ATM where ATM_ID = #{atmId}")
    Atm findById(int atmId);
}
