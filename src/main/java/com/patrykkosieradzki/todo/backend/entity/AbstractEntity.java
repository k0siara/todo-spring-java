package com.patrykkosieradzki.todo.backend.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class AbstractEntity implements Serializable {

    @NonNull
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
