package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.*;
import tech.ioco.banking.model.AtmAllocation;

import java.util.List;

@Mapper
public interface AtmAllocationMapper {
    @Results({
            @Result(id = true, property = "atmAllocationId", column = "ATM_ALLOCATION_ID"),
            @Result(property = "atm", column = "ATM_ID", one = @One(select = "tech.ioco.banking.mapper.AtmMapper.findById")),
            @Result(property = "denomination", column = "DENOMINATION_ID", one = @One(select = "tech.ioco.banking.mapper.DenominationMapper.findById"))
    })
    @Select("select * from ATM_ALLOCATION where ATM_ID = #{atmId}")
    List<AtmAllocation> findAllByAtmId(int atmId);
}
