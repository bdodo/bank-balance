package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.*;
import tech.ioco.banking.model.Denomination;

import java.util.List;

@Mapper
public interface DenominationMapper {

    @Results({
            @Result(id = true, property = "denominationId", column = "DENOMINATION_ID"),
            @Result(property = "denominationType", column = "DENOMINATION_TYPE_CODE", one = @One(select = "tech.ioco.banking.mapper.DenominationTypeMapper.findByCode"))
    })
    @Select("select * from DENOMINATION where DENOMINATION_TYPE_CODE = #{typeCode}")
    List<Denomination> findByDenominationTypeCode(String typeCode);

    @Results({
            @Result(id = true, property = "denominationId", column = "DENOMINATION_ID"),
            @Result(property = "denominationType", column = "DENOMINATION_TYPE_CODE", one = @One(select = "tech.ioco.banking.mapper.DenominationTypeMapper.findByCode"))
    })
    @Select("select * from DENOMINATION where DENOMINATION_ID = #{denominationId}")
    Denomination findById(int denominationId);
}
