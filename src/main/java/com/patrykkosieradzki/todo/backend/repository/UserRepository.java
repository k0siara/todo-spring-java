package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(@Param("id") Long id);

}
