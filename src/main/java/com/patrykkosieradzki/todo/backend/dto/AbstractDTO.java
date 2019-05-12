package com.patrykkosieradzki.todo.backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractDTO implements Serializable {

    private Long id;

}
