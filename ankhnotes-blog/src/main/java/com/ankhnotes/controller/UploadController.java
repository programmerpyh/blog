package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.service.OssUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    OssUploadService ossUploadService;

    //上传头像到七牛云OSS
    @mySystemLog(logDescription = "上传头像到七牛云OSS")
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return ossUploadService.uploadImg(img);
    }

}
