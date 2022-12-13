package com.example.todayisdiary.domain.image.controller;

import com.example.todayisdiary.global.s3.facade.S3Facade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "image", description = "이미지에 대한 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final S3Facade s3Facade;

    @Operation(summary = "프로필 이미지 등록-회원가입")
    @PostMapping("/sign/user")
    public String userProFile(@RequestParam(name = "images") MultipartFile file){
        return s3Facade.uploadImage(file);
    }

    @Operation(summary = "이미지 등록")
    @PostMapping("/upload")
    public String upLoad(@RequestParam(name = "images") MultipartFile file){
        return s3Facade.uploadImage(file);
    }

    @Operation(summary = "프로필 이미지 변경")
    @PutMapping("/user")
    public void userUploadProfile(@RequestParam(name = "images") MultipartFile file){
        s3Facade.updateUser(file);
    }

    @Operation(summary = "일기 이미지 변경")
    @PutMapping("/diary/{diary_id}")
    public void diaryUpload(@RequestParam(name = "images")MultipartFile file, @PathVariable(name = "diary_id")Long id){
        s3Facade.updateBoard(file, id);
    }
}
