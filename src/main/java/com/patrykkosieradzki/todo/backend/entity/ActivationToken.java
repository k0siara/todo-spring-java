package com.patrykkosieradzki.todo.backend.entity;

import com.patrykkosieradzki.todo.backend.util.TokenUtils;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ActivationToken extends AbstractEntity {

    @NonNull
    private String value;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ActivationToken create() {
        ActivationToken activationToken = new ActivationToken();
        activationToken.setValue(TokenUtils.getRandomToken(32));
        activationToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        return activationToken;
    }

}


