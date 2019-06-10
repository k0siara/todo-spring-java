package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoRepository {

    @Select("SELECT * FROM todos WHERE id = #{id}")
    @Results({
            @Result(property = "user", javaType = User.class, column = "user_id",
                    one = @One(select = AppConstants.FIND_USER_BY_ID_PATH))
    })
    Optional<Todo> findById(Long id);

    @Select("SELECT * FROM todos " +
            "WHERE id > #{pageNumber} * #{pageSize} " +
            "AND id <= (#{pageNumber} + 1) * #{pageSize}")
    @Results({
            @Result(property = "user", javaType = User.class, column = "user_id",
                    one = @One(select = AppConstants.FIND_USER_BY_ID_PATH))
    })
    List<Todo> findAll();

    @Insert("INSERT INTO todos(text, user_id) values (#{text}, #{user.id})")
    @SelectKey(statement = "SELECT SCOPE_IDENTITY() as id", keyProperty = "id", before = false, resultType = Long.class)
    void save(Todo todo);

    @Select("SELECT t.id, t.text, t.user_id, t.is_done, t.timestamp FROM TODOS t " +
            "join users u on t.user_id = u.id " +
            "WHERE u.username = #{username} " +
            "AND id > #{pageNumber} * #{pageSize} " +
            "AND id <= (#{pageNumber} + 1) * #{pageSize}")
    @Results({
            @Result(property = "user", javaType = User.class, column = "user_id",
                    one = @One(select = AppConstants.FIND_USER_BY_ID_PATH))
    })
    List<Todo> findAllByUserUsernameWithPageable(String username, Pageable pageable);

    @Select("SELECT t.id, t.text, t.user_id, t.is_done, t.timestamp FROM TODOS t " +
            "join users u on t.user_id = u.id " +
            "WHERE u.username = #{username}")
    @Results({
            @Result(property = "user", javaType = User.class, column = "user_id",
                    one = @One(select = AppConstants.FIND_USER_BY_ID_PATH))
    })
    List<Todo> findAllByUserUsername(String username);

    @Update("UPDATE todos SET text = #{text}, is_done = #{isDone}, timestamp = #{timestamp} WHERE id = #{id}")
    void update(Todo todo);

    @Delete("DELETE todos WHERE user_id = #{user.id} AND is_done = #{done}")
    void deleteByDone(User user, boolean done);

    @Delete("DELETE todos WHERE id = #{id}")
    void deleteById(Long id);
}
