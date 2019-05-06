package com.patrykkosieradzki.todo.backend.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class AbstractEntity implements Serializable {

    @NonNull
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
