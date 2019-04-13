package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO users(id, first_name, last_name, username, email, password) " +
            "values(#{id}, #{firstName}, #{lastName}, #{username}, #{email}, #{password})")
    void save(User user);
}
