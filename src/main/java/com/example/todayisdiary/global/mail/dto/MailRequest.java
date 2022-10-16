package com.example.todayisdiary.global.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
@AllArgsConstructor
public class MailDto {
    @Email
    private String adress;
}
