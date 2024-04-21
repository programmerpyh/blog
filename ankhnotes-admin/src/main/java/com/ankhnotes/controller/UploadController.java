package com.ankhnotes.controller;


import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.service.OssUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    OssUploadService ossUploadService;

    @mySystemLog(logDescription = "写博客页面上传缩略图")
    @PostMapping("/upload")
    //@RequestParam 在POST中用于接收请求头为multipart/form-data的方法体
    //@RequestBody 在POST中用于接收请求头为json/application的方法体
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        return ossUploadService.uploadImg(img);
    }

}
