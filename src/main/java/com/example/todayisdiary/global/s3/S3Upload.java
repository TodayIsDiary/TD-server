package com.example.todayisdiary.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.error.ErrorCode;
import com.example.todayisdiary.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final BoardFacade boardFacade;
    private final UserFacade userFacade;
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public String uploadImage(MultipartFile multipartFile){
        return upload(multipartFile);
    }

    public void updateUser(MultipartFile multipartFile){
        User user = userFacade.getCurrentUser();
        if(user.getImageUrl() != null){
            amazonS3.deleteObject(bucket,user.getImageUrl());
        }
        user.userProfileChange(upload(multipartFile));
        userRepository.save(user);
    }

    public void updateBoard(MultipartFile multipartFile, Long id){
        Board board = boardFacade.getBoardById(id);
        userMatch(board);
        if(board.getImageUrl() != null){
            amazonS3.deleteObject(bucket,board.getImageUrl());
        }
        board.changeImage(upload(multipartFile));
        boardRepository.save(board);
    }

    public void delUser(User user){
        if(user.getImageUrl() != null){
            amazonS3.deleteObject(bucket,user.getImageUrl());
        }
    }

    public void delBoard(Board board){
        if(board.getImageUrl() != null){
            amazonS3.deleteObject(bucket,board.getImageUrl());
        }
    }

    public String getImageUrl(String s3FileName){
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    private String upload(MultipartFile multipartFile){
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        try {
            objMeta.setContentLength(multipartFile.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s3FileName;
    }

    private void userMatch(Board board) {
        if (board.getUser().getAccountId().equals(userFacade.getCurrentUser().getAccountId()) || userFacade.getCurrentUser().getRole() == Role.ROLE_ADMIN) {
        } else throw new CustomException(ErrorCode.USER_MISS_MATCHED);
    }
}
