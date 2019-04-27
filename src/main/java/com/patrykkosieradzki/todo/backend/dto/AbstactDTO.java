package com.patrykkosieradzki.todo.backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstactDTO implements Serializable {

    private Long id;

}
