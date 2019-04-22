package com.patrykkosieradzki.todo.backend.mail;

public interface EmailService {

    void sendMessage(String to, String subject, String text);
}
