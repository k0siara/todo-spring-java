package com.patrykkosieradzki.todo.backend.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AbstractEntity implements Serializable {

    private Long id;

}
