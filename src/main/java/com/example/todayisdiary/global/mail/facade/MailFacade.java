package com.example.todayisdiary.global.mail.facade;

import com.example.todayisdiary.global.error.ErrorCode;
import com.example.todayisdiary.global.error.exception.CustomException;
import com.example.todayisdiary.global.mail.entity.Mail;
import com.example.todayisdiary.global.mail.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailFacade {
    private final MailRepository mailRepository;

    public Mail getMail(String email){
        return mailRepository.findMailByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
    }
}
