package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.backend.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> findById(@Param("id") Long id);

    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<User> findByUsername(@Param("username") String username);

    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(@Param("email") String email);

    @Select("SELECT * FROM users WHERE activation_token = #{activationToken}")
    Optional<User> findByActivationToken(@Param("activationToken") String activationToken);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Update("UPDATE users SET is_enabled = true WHERE id = #{id}")
    void enable(User user);

    @Insert("INSERT INTO users(id, first_name, last_name, username, email, password) " +
            "values(#{id}, #{firstName}, #{lastName}, #{username}, #{email}, #{password})")
    void save(User user);

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE ${fieldName} = #{value})")
    boolean existsByFieldName(@Param("fieldName") String fieldName, @Param("value") String value);
}
