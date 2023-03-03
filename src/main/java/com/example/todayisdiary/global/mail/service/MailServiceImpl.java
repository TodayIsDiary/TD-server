package com.example.todayisdiary.global.mail.service;

import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.mail.dto.MailRequest;
import com.example.todayisdiary.global.mail.entity.Mail;
import com.example.todayisdiary.global.mail.facade.MailFacade;
import com.example.todayisdiary.global.mail.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;
    private final UserRepository userRepository;
    private final MailFacade mailFacade;

    public String randomMessage(String accountId) {

        SecureRandom random = new SecureRandom();
        User user = userRepository.findUserByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));

        boolean exits = mailRepository.existsByEmail(user.getEmail());
        if(exits) {
            Mail mail = mailFacade.getMail(user.getEmail());
            mailRepository.delete(mail);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }

        Mail mail = new Mail(
            sb.toString(),
            user.getEmail(),
            user
        );
        mailRepository.save(mail);
        return sb.toString();
    }

    @Override
    public void mailSend(MailRequest request, String accountId)throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO,request.getEmail()); // 보내는 대상
        message.setSubject("'하루의끝' 인증 코드" );

        String msgg="";
        msgg+= "<div class=\"\"><div class=\"aHl\"></div><div id=\":2j\" tabindex=\"-1\"></div><div id=\":2q\" class=\"ii gt\" jslog=\"20277; u014N:xr6bB; 4:W251bGwsbnVsbCxbXV0.\"><div id=\":1l\" class=\"a3s aiL \"><div class=\"adM\">\n" +
                "\n" +
                "\t\n" +
                "\n" +
                "</div><div><div class=\"adM\">\n" +
                "\t</div><table style=\"background-color:#f2f2f2;border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;text-align:center;width:100%\">\n" +
                "\t\t<tbody><tr><td height=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t<center>\n" +
                "\t\t\t\t\t<table style=\"background-color:#fff;border-collapse:collapse;border-spacing:0;;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;text-align:left;width:720px\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <img style=\"width:720px;height:180px;\" src=\"https://cdn.discordapp.com/attachments/822309850700054548/1031186200612048927/TD.jpg\"/>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;width:100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "                                    <tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"10\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"10\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;width:100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:20px 0;padding:0\"><strong style=\"color:#333\">안녕하세요. <em style=\"color:#6C1FD3;font-style:normal\">하루의 끝</em> 입니다.</strong></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:20px 0;padding:0\">" + accountId +"님이 신청하신 서비스는 본인확인을 위해 <span class=\"il\">인증</span><span class=\"il\">번호</span> 확인이 필요합니다. <br>아래의 <span class=\"il\">인증</span><span class=\"il\">번호</span>를 복사하신 후 이메일 <span class=\"il\">인증</span><span class=\"il\">번호</span> 입력란에 입력해 주시기 바랍니다. <br><span class=\"il\"></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<div style=\"background-color:#f7f7f7;border-bottom:1px solid #e7e7e7;border-top:1px solid #e7e7e7;color:#666;font-size:16px;padding:35px;text-align:center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<span><strong style=\"color:#000;font-weight:bold;font-size:30px;\">" + randomMessage(accountId) + "</strong></span>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:20px 0;padding:0\"><strong style=\"color:#333\">하루의 끝을 이용해 주셔서 감사합니다.</strong></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"30\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"border-top:1px solid #d9d9d9;color:#999\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;width:100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"40\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0;padding:0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t타인이 회원님의 이메일을 잘못 입력한 경우 메일이 발송될 수 있습니다. <br>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t잘못 수신된 메일이라면 todayisdiary@gmail.com에 문의해주시길 바랍니다.\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"font-size:11px;margin:0;margin-top:10px;padding:0\">© TodayIsDiary. All Rights Reserved.</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"40\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t</center>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td height=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "</div></div><div class=\"adL\">\n" +
                "</div></div></div><div id=\":2f\" class=\"ii gt\" style=\"display:none\"><div id=\":2e\" class=\"a3s aiL \"></div></div><div class=\"hi\"></div></div>";

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("todayisdiary@gmail.com","하루의끝"));//보내는 사람

        javaMailSender.send(message);
    }

    public String randomSignupMessage(String email) {

        boolean exits = mailRepository.existsByEmail(email);
        if(exits) {
            Mail mail = mailFacade.getMail(email);
            mailRepository.delete(mail);
        }

        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }

        Mail mail = new Mail(
                sb.toString(),
                email
        );
        mailRepository.save(mail);
        return sb.toString();
    }

    @Override
    public void signMailSend(MailRequest request)throws Exception{
        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (exists) throw new IllegalStateException("이미 가입하신 이메일 입니다.");
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO,request.getEmail()); // 보내는 대상
        message.setSubject("'하루의끝' 인증 코드" );

        String msgg="";
        msgg+= "<div class=\"\"><div class=\"aHl\"></div><div id=\":2j\" tabindex=\"-1\"></div><div id=\":2q\" class=\"ii gt\" jslog=\"20277; u014N:xr6bB; 4:W251bGwsbnVsbCxbXV0.\"><div id=\":1l\" class=\"a3s aiL \"><div class=\"adM\">\n" +
                "\n" +
                "\t\n" +
                "\n" +
                "</div><div><div class=\"adM\">\n" +
                "\t</div><table style=\"background-color:#f2f2f2;border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;text-align:center;width:100%\">\n" +
                "\t\t<tbody><tr><td height=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t<center>\n" +
                "\t\t\t\t\t<table style=\"background-color:#fff;border-collapse:collapse;border-spacing:0;;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;text-align:left;width:720px\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <img style=\"width:720px;height:180px;\" src=\"https://cdn.discordapp.com/attachments/822309850700054548/1031186200612048927/TD.jpg\"/>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;width:100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "                                    <tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"10\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"10\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;width:100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:20px 0;padding:0\"><strong style=\"color:#333\">안녕하세요. <em style=\"color:#6C1FD3;font-style:normal\">하루의 끝</em> 입니다.</strong></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:20px 0;padding:0\">" + "신청하신 서비스는 본인확인을 위해 <span class=\"il\">인증</span><span class=\"il\">번호</span> 확인이 필요합니다. <br>아래의 <span class=\"il\">인증</span><span class=\"il\">번호</span>를 복사하신 후 이메일 <span class=\"il\">인증</span><span class=\"il\">번호</span> 입력란에 입력해 주시기 바랍니다. <br><span class=\"il\"></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<div style=\"background-color:#f7f7f7;border-bottom:1px solid #e7e7e7;border-top:1px solid #e7e7e7;color:#666;font-size:16px;padding:35px;text-align:center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<span><strong style=\"color:#000;font-weight:bold;font-size:30px;\">" + randomSignupMessage(request.getEmail()) + "</strong></span>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:20px 0;padding:0\"><strong style=\"color:#333\">하루의 끝을 이용해 주셔서 감사합니다.</strong></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"30\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"border-top:1px solid #d9d9d9;color:#999\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"border-collapse:collapse;border-spacing:0;font-family:'Malgun Gothic','Lucida Grande','Lucida Sans','Lucida Sans Unicode',Arial,Helvetica,Verdana,sans-serif;font-size:12px;width:100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"40\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td style=\"color:#999\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin:0;padding:0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t타인이 회원님의 이메일을 잘못 입력한 경우 메일이 발송될 수 있습니다. <br>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t잘못 수신된 메일이라면 todayisdiary@gmail.com에 문의해주시길 바랍니다.\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<p style=\"font-size:11px;margin:0;margin-top:10px;padding:0\">© TodayIsDiary. All Rights Reserved.</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td width=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td height=\"40\" colspan=\"3\" style=\"color:#999\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t</center>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td height=\"40\" style=\"color:#999\"></td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "</div></div><div class=\"adL\">\n" +
                "</div></div></div><div id=\":2f\" class=\"ii gt\" style=\"display:none\"><div id=\":2e\" class=\"a3s aiL \"></div></div><div class=\"hi\"></div></div>";

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("todayisdiary@gmail.com","하루의끝"));//보내는 사람

        javaMailSender.send(message);

    }
}
