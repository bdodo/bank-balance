package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tech.ioco.banking.model.DenominationType;

@Mapper
public interface DenominationTypeMapper {
    @Select("select * from DENOMINATION_TYPE where DENOMINATION_TYPE_CODE = #{code}")
    DenominationType findByCode(String code);
}
