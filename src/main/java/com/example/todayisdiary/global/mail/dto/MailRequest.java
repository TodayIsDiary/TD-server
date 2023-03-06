package com.example.todayisdiary.global.mail.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@RequiredArgsConstructor
public class MailRequest {
    private String email;
}
