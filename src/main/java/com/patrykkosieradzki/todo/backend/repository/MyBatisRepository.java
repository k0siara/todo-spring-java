package com.patrykkosieradzki.todo.backend.repository;

import org.apache.ibatis.annotations.SelectKey;

import java.util.List;
import java.util.Optional;

public interface MyBatisRepository<T, ID> {

    Optional<T> findById(ID id);

    List<T> findAll();

    void save(T t);

    void update(T t);
}
