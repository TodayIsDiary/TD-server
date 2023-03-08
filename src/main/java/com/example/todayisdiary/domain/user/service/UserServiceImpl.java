package com.example.todayisdiary.domain.user.service;

import com.example.todayisdiary.domain.user.dto.*;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.date.DateServiceImpl;
import com.example.todayisdiary.global.error.ErrorCode;
import com.example.todayisdiary.global.error.exception.CustomException;
import com.example.todayisdiary.global.mail.dto.MailRequest;
import com.example.todayisdiary.global.mail.entity.Mail;
import com.example.todayisdiary.global.mail.repository.MailRepository;
import com.example.todayisdiary.global.mail.service.MailService;
import com.example.todayisdiary.global.s3.facade.S3Facade;
import com.example.todayisdiary.global.security.oauth.entity.ProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
    private final DateServiceImpl dateService;

    @Override
    @Transactional
    public void checkSignup(CheckRequest request){
        if(!request.getChek()) {throw new CustomException(ErrorCode.ACCOUNT_ID_NOT_CHECK);}

        boolean exists = userRepository.existsByEmail(request.getEmail());
        if (exists) throw new CustomException(ErrorCode.EXIST_EMAIL);

        Mail mail = mailRepository.findMailByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_MISS_MATCHED));

        if (!mail.getCode().equals(request.getCode())) {
            throw new CustomException(ErrorCode.CODE_MISS_MATCHED);
        }else mailRepository.delete(mail);

        if(dateService.invalidCode(mail.getCreateTime())){
            throw new CustomException(ErrorCode.CODE_EXPIRED);
        }

        if (!request.getPassword().equals(request.getPasswordValid())) {
            throw new CustomException(ErrorCode.PASSWORD_MISS_MATCHED);
        }

    }

    @Override
    public void signup(SignupRequest request) {

        User user = User.builder()
                .accountId(request.getAccountId())
                .nickName(request.getNickName())
                .email(request.getEmail())
                .sex(request.getSex())
                .password(passwordEncoder.encode(request.getPassword()))
                .imageUrl(defaultImage(request.getImageUrl()))
                .introduction(request.getIntroduction())
                .providerType(ProviderType.LOCAL).build();

        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserResponse login(LoginRequest request) {

        User user = userFacade.getUserByAccountId(request.getAccountId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISS_MATCHED);
        }

        return UserResponse.of(user);
    }

    @Transactional
    @Override
    public boolean newNickName(String accountId) {
        boolean exists = userRepository.existsByAccountId(accountId);
        if (exists) throw new CustomException(ErrorCode.EXIST_USER);
        else return true;
    }

    @Override
    public void lostPassword(MailRequest mailDto) throws Exception {

        User user = userFacade.getUserByEmail(mailDto.getEmail());

        mailService.mailSend(mailDto, user.getAccountId());

    }

    @Override
    public void setPasswordCodeCheck(PasswordCheckRequest request){
        Mail mail = mailRepository.findMailByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_MISS_MATCHED));

        if (!mail.getCode().equals(request.getCode())) {
            throw new CustomException(ErrorCode.CODE_MISS_MATCHED);
        }else mailRepository.delete(mail);
    }

    @Transactional
    @Override
    public void setPasswordEmail(PasswordRequest request) {

        User user = userFacade.getUserByEmail(request.getEmail());

        if (!request.getNewPassword().equals(request.getNewPasswordValid())) {
            throw new CustomException(ErrorCode.PASSWORD_MISS_MATCHED);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setPasswords(PasswordRequest request) {
        User user = userFacade.getCurrentUser();

        if (!passwordEncoder.matches(request.getOriginalPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.ORIGIN_PASSWORD_MISS_MATCHED);
        }

        if (!request.getNewPassword().equals(request.getNewPasswordValid())) {
            throw new CustomException(ErrorCode.CHANGE_PASSWORD_MISS_MATCHED);
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
    public UserInfoResponse getUser(Long id) {
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

        s3Facade.delUser(user);
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void signupEmail(MailRequest mailDto) throws Exception {

        mailService.signMailSend(mailDto);

    }

    private UserInfoResponse myPage(User user) {
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

    private String defaultImage(String imageUrl){
        if(Objects.equals(imageUrl, "null")){return "4f743a16-e96f-49e7-9c11-0948592dab18-5087579.png";}
        else return imageUrl == null ? "4f743a16-e96f-49e7-9c11-0948592dab18-5087579.png" : imageUrl;
    }
}
