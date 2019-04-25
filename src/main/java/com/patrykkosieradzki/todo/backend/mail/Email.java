package com.patrykkosieradzki.todo.backend.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String from;
    private String to;
    private String subject;
    private String text;
    private boolean isMultipart;

}
