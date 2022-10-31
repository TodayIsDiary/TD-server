package com.example.todayisdiary.global.mail.service;

import com.example.todayisdiary.global.mail.dto.MailRequest;

public interface MailService {
    void mailSend(MailRequest request, String accountId)throws Exception;

    void signMailSend(MailRequest request)throws Exception;
}
