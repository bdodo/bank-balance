package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.*;
import tech.ioco.banking.model.Client;

@Mapper
public interface ClientMapper {

    @Select("select * from CLIENT where CLIENT_ID = #{id}")
    @Results({
            @Result(id = true, property = "clientId", column = "CLIENT_ID"),
            @Result(property = "clientSubType", column = "CLIENT_SUB_TYPE_CODE", one = @One(select = "tech.ioco.banking.mapper.ClientSubTypeMapper.findByCode"))
    })
    public Client findById(int id);
}
