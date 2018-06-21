package io.jmerta.tau.repository;

import io.jmerta.tau.domain.accountManagment.entity.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SessionRepository {

    @Insert("insert into public.account_session (account_id, valid_until, token) values (#{account.id:INTEGER},#{account.session.validUntil}, #{account.session.token:VARCHAR})")
    void saveSession(@Param("account") Account session);

    @Select("select account_id from public.account_session where token = #{token:VARCHAR}")
    long getAccountId(@Param("token")String token);

    @Delete("delete from account_session where account_id = #{account.id:INTEGER}")
    void destroySession(@Param("account")Account account);

}
