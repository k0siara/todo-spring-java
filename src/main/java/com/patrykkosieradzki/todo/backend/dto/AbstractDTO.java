package com.patrykkosieradzki.todo.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class AbstractDTO implements Serializable {

    private Long id;

}
