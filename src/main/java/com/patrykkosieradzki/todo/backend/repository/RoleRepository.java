package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.backend.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleRepository {

    @Select("SELECT r.id, r.name, r.created_at, r.updated_at FROM roles r " +
            "join user_roles ur on r.id = ur.role_id " +
            "join users u on ur.user_id = u.id " +
            "where u.id = #{userId}")
    List<Role> findAllByUserId(Long userId);

}
