package io.jmerta.tau.repository;

import io.jmerta.tau.domain.taskManagment.entity.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardRepository {

    @Insert("insert into public.board (name,account_id) values (#{board.name:VARCHAR}, #{board.accountId:INTEGER})")
    long createNewBoard(@Param("board")Board board);

    @Delete("delete from public.board where id = #{id:INTEGER}")
    long deleteBoard(@Param("id") long id);

    @Update("update public.board set name = #{board.name:VARCHAR} where id = #{board.id}")
    long updateBoard(@Param("board") Board board);

    @Select("select * from public.board where account_id = #{accountId:INTEGER}")
    @Results(id = "boardResults", value =
            {
                    @Result(property = "id", column = "id"),
                    @Result(property = "accountId", column = "account_id"),
                    @Result(property = "name", column = "name")
            })
    List<Board> getAllBoardsByUser(@Param("accountId") long accountId);

    @Select("select * from public.board where id = #{id}")
    @ResultMap("boardResults")
    Board getBoardById(@Param("id") long id);
}
