package com.patrykkosieradzki.todo.backend.repository;

import com.patrykkosieradzki.todo.backend.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoleRepository {

    @Select("SELECT * FROM roles WHERE id = #{id}")
    Optional<Role> findById(Long id);

    @Select("SELECT r.id, r.name, r.created_at, r.updated_at FROM roles r " +
            "JOIN user_roles ur on r.id = ur.role_id " +
            "JOIN users u on ur.user_id = u.id " +
            "WHERE u.id = #{id}")
    List<Role> findAllByUserId(Long id);

}
