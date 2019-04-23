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

    @Select("SELECT u.id, u.first_name, u.last_name, u.username, u.email, u.password, u.is_expired, u.is_locked," +
            "u.is_credentials_expired, u.is_enabled, u.activation_token_id, u.created_at, u.updated_at FROM users u " +
            "JOIN activation_tokens at on u.activation_token_id = at.id " +
            "WHERE at.value = #{activationToken}")
    Optional<User> findByActivationToken(@Param("activationToken") String activationToken);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO users(first_name, last_name, username, email, password, is_expired, is_locked, " +
            "is_credentials_expired, is_enabled, created_at, updated_at, activation_token_id) " +
            "values (#{firstName}, #{lastName}, #{username}, #{email}, #{password}, #{isExpired}, #{isLocked}, " +
            "#{isCredentialsExpired}, #{isEnabled}, #{createdAt}, #{updatedAt}, #{activationTokenId})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() as id", keyProperty = "id", before = false, resultType = Long.class)
    void save(User user);

    @Update("UPDATE users SET first_name = #{firstName}, last_name = #{lastName}, username = #{username}, " +
            "email = #{email}, password = #{password}, is_expired = #{isExpired}, is_locked = #{isLocked}, " +
            "is_credentials_expired = #{isCredentialsExpired}, is_enabled = #{isEnabled}, created_at = #{createdAt}, " +
            "updated_at = #{updatedAt}, activation_token_id = #{activationTokenId} WHERE id = #{id}")
    void update(User user);

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE ${fieldName} = #{value})")
    boolean existsByFieldName(@Param("fieldName") String fieldName, @Param("value") String value);
}
