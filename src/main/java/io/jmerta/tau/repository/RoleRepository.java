package io.jmerta.tau.repository;

import io.jmerta.tau.domain.accountManagment.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleRepository {


    @Select("Select * from public.role")
    @Results(id = "roleResult", value =
            {
                    @Result(property = "id", column = "id"),
                    @Result(property = "authority", column = "name"),
                    @Result(property = "code", column = "code")
            })
    List<Role> getAllRoles();


}
