package com.patrykkosieradzki.todo.backend.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String from;
    private String to;
    private String subject;
    private String text;
    private boolean isMultipart;

}
