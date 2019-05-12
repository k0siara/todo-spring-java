package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoRepository {

    @Select("SELECT * FROM todos WHERE id = #{id}")
    Optional<Todo> findById(Long id);

    @Select("SELECT * FROM todos")
    List<Todo> findAll();

    @Insert("INSERT INTO todos(text, user_id) " +
            "values (#{text}, #{userId})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() as id", keyProperty = "id", before = false, resultType = Long.class)
    void save(Todo todo);

    @Select("SELECT * FROM todos WHERE user_id = #{userId}")
    List<Todo> findAllByUserId(Long userId);

    @Update("UPDATE todos SET text = #{text}, is_done = #{isDone}, timestamp = #{timestamp} WHERE id = #{id}")
    void update(Todo todo);

    @Delete("DELETE todos WHERE user_id = #{user.id} AND is_done = #{done}")
    void deleteByDone(User user, boolean done);

    @Delete("DELETE todos WHERE id = #{id}")
    void deleteById(Long id);
}
