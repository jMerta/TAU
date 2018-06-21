package io.jmerta.tau.repository;

import io.jmerta.tau.domain.accountManagment.entity.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountRepository {

    @Select("Select * from public.account where upper(username) = upper(#{username:VARCHAR})")
    @Results(id = "accountResult", value =
            {
                    @Result(property = "id", column = "id"),
                    @Result(property = "username", column = "username"),
                    @Result(property = "passwordSalt", column = "password_salt"),
                    @Result(property = "roleId",column = "role_id")
            })
    Account getAccountByUsername(@Param("username") String username);

    @Select("Select * from public.account")
    @ResultMap("accountResult")
    List<Account> getAllAccounts();


    @Select("Select * from public.account a join public.account_session b on a.id = b.account_id  where a.id = (select account_id from public.account_session b  where b.token = #{token:VARCHAR})")
    @ResultMap("accountResult")
    Account getAccountByToken(@Param("token") String token);

    @Insert("Insert into account (username, password, passsword_salt, role_id) values (#{account.username:VARCHAR}, #{account.password:VARCHAR}, #{account.passwordSalt:VARCHAR}, (select id from public.role where upper(code) = 'USER'))")
    @Options(useGeneratedKeys = true,keyProperty = "account.id",keyColumn = "id")
    Long createAccount(@Param("account") Account account);


    @Select("Select * from public.account where id = #{id}")
    @ResultMap("accountResult")
    Account getAccount(@Param("id") long id);

}
