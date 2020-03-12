package tech.ioco.banking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import tech.ioco.banking.model.ClientType;

@Mapper
public interface ClientTypeMapper {

    @Select("select * from CLIENT_TYPE where CLIENT_TYPE_CODE = #{code}")
    @Result(id = true, property = "clientTypeCode", column = "CLIENT_TYPE_CODE")
    ClientType findByCode(String code);
}
