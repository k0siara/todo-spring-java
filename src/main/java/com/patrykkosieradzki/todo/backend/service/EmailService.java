package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.backend.mail.Email;

public interface EmailService {

    void sendMessage(Email email);
}
