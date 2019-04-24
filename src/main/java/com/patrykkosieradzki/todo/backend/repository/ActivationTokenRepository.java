package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.backend.entity.ActivationToken;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface ActivationTokenRepository {

    @Select("SELECT * FROM activation_tokens WHERE id = #{id}")
    Optional<ActivationToken> findById(@Param("id") Long id);

    @Insert("INSERT INTO activation_tokens (value, expires_at)" +
            "values (#{value}, #{expiresAt})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() as id", keyProperty = "id", before = false, resultType = Long.class)
    void save(ActivationToken activationToken);
}
