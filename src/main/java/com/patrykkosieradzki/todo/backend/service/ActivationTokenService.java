package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.backend.entity.ActivationToken;
import com.patrykkosieradzki.todo.backend.repository.ActivationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class ActivationTokenService implements HasLogger {

    private ActivationTokenRepository activationTokenRepository;

    @Autowired
    public ActivationTokenService(ActivationTokenRepository activationTokenRepository) {
        this.activationTokenRepository = activationTokenRepository;
    }

    public ActivationToken create() {
        ActivationToken activationToken = new ActivationToken();

        String randomToken = UUID.randomUUID().toString().replaceAll("-", "");
        getLogger().debug("generated random activation token: " + randomToken);
        activationToken.setValue(randomToken);

        LocalDateTime now = LocalDateTime.now();
        activationToken.setCreatedAt(now);
        activationToken.setUpdatedAt(now);
        activationToken.setExpiresAt(now.plus(7, ChronoUnit.DAYS));

        activationTokenRepository.save(activationToken);
        getLogger().debug("inserted new activation token with id: " + activationToken.getId());

        return activationTokenRepository.findById(activationToken.getId())
                .orElseThrow(() -> new RuntimeException("ActivationToken not found"));
    }
}

