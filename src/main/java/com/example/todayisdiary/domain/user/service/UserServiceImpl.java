package com.example.todayisdiary.domain.user.service;

import com.example.todayisdiary.domain.user.dto.*;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.mail.dto.MailRequest;
import com.example.todayisdiary.global.mail.entity.Mail;
import com.example.todayisdiary.global.mail.repository.MailRepository;
import com.example.todayisdiary.global.mail.service.MailService;
import com.example.todayisdiary.global.s3.facade.S3Facade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserFacade userFacade;
    private final S3Facade s3Facade;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final MailRepository mailRepository;

    @Override
    public void signup(SignupRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        boolean exists2 = userRepository.existsByAccountId(request.getAccountId());
        if (exists) throw new IllegalStateException("이미 가입하신 이메일 입니다.");
        if (exists2) throw new IllegalStateException("이미 있는 아이디 입니다.");

        Mail mail = mailRepository.findMailByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원가입하는 이메일이 아닙니다."));

        if (!mail.getCode().equals(request.getCode())) {
            throw new IllegalStateException("인증 코드가 다릅니다.");
        }

        User user = User.builder()
                .accountId(request.getAccountId())
                .nickName(request.getNickName())
                .email(request.getEmail())
                .sex(request.getSex())
                .password(passwordEncoder.encode(request.getPassword()))
                .introduction(request.getIntroduction())
                .imageUrl(request.getImageUrl()).build();

        userRepository.save(user);
        mailRepository.delete(mail);
    }

    @Transactional
    @Override
    public UserResponse login(LoginRequest request) {

        User user = userFacade.getUserByAccountId(request.getAccountId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 맞지 않습니다.");
        }

        return UserResponse.of(user);
    }

    @Override
    public void lostPassword(MailRequest mailDto) throws Exception {

        User user = userFacade.getUserByEmail(mailDto.getEmail());

        mailService.mailSend(mailDto, user.getAccountId());

    }

    @Transactional
    @Override
    public void setPasswordEmail(PasswordRequest request) {

        Mail mail = mailRepository.findMailByCode(request.getCode())
                .orElseThrow(() -> new IllegalArgumentException("코드를 다시 입력 해주세요.."));

        User user = userFacade.getUserByEmail(mail.getEmail());

        if (!request.getNewPassword().equals(request.getNewPasswordValid())) {
            throw new IllegalStateException("비밀번호가 맞지 않습니다.");
        }

        log.info("비밀번호가 {}로 바꼈습니다.", request.getNewPassword());
        mailRepository.delete(mail);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setPasswords(PasswordRequest request){
        User user = userFacade.getCurrentUser();

        if(!passwordEncoder.matches(request.getOriginalPassword(), user.getPassword())){
            throw new IllegalStateException("원래 비밀번호가 맞지 않습니다.");
        }

        if(!request.getNewPassword().equals(request.getNewPasswordValid())){
            throw new IllegalStateException("변경하는 비밀번호가 맞지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse myUser() {
        User user = userFacade.getCurrentUser();
        return myPage(user);
    }

    @Override
    @Transactional()
    public UserInfoResponse getUser(Long id){
        User user = userFacade.getUserById(id);
        user.addVisit();
        return myPage(user);
    }

    @Override
    @Transactional
    public void setUser(UserRequest request) {

        User user = userFacade.getCurrentUser();

        user.setUser(request.getNickName(), request.getIntroduction());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void leaveUser() {

        User user = userFacade.getCurrentUser();

        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void signupEmail(MailRequest mailDto) throws Exception {

        mailService.signMailSend(mailDto);

    }

    private UserInfoResponse myPage(User user){
        return UserInfoResponse.builder()
                .nickName(user.getNickName())
                .introduction(user.getIntroduction())
                .comment(user.getComments().size())
                .love(user.getBoardLoves().size() + user.getCommentLoves().size())
                .visit(user.getVisit())
                .sex(user.getSex())
                .email(user.getEmail())
                .imagUrl(s3Facade.getUrl(user.getImageUrl())).build();
    }
}
