package com.example.todayisdiary.global.s3.facade;

import com.example.todayisdiary.global.s3.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Facade {
    private final S3Upload s3Upload;

    public String uploadImage(MultipartFile multipartFile){
        return s3Upload.uploadImage(multipartFile);
    }

    public void updateUser(MultipartFile multipartFile){
        s3Upload.updateUser(multipartFile);
    }

    public String getUrl(String s3FileName){
        return s3Upload.getImageUrl(s3FileName);
    }

    public void updateBoard(MultipartFile multipartFile, Long id){
        s3Upload.updateBoard(multipartFile, id);
    }
}
