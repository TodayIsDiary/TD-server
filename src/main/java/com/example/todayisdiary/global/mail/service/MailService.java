package com.example.todayisdiary.global.mail.service;

import com.example.todayisdiary.global.mail.dto.MailDto;
import com.example.todayisdiary.global.mail.entity.Mail;
import com.example.todayisdiary.global.mail.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;

    private String randomMessage(String accountId){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<4; i++){
            sb.append(random.nextInt(10));
        }
        Mail mail = Mail.builder()
                .code(sb.toString())
                .accountId(accountId).build();
        mailRepository.save(mail);
        return sb.toString();
    }

    public void mailSend(MailDto mailDto,String accountId){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAdress()); //받는 사람의 주소
        message.setSubject("'하루의끝' 인증 코드"); // 제목
        message.setText(accountId + "님 오늘도 즐거운 하루였나요??\n" + "인증번호는 ' " + randomMessage(accountId) + " ' 입니다.\n내일도 만나요!!"); // 내용
        javaMailSender.send(message); //메일 발송
    }
}
