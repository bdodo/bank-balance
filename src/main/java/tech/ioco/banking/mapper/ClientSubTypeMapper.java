package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.*;
import tech.ioco.banking.model.ClientSubType;

@Mapper
public interface ClientSubTypeMapper {
    @Select("select * from CLIENT_SUB_TYPE where CLIENT_SUB_TYPE_CODE = #{code}")
    @Results({
            @Result(id = true, property = "clientSubTypeCode", column = "CLIENT_SUB_TYPE_CODE"),
            @Result(property = "clientType", column = "CLIENT_TYPE_CODE", one = @One(select = "tech.ioco.banking.mapper.ClientTypeMapper.findByCode"))
    })
    ClientSubType findByCode(String code);
}
