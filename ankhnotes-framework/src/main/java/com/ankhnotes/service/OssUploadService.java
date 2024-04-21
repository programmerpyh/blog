package com.ankhnotes.service;

import com.ankhnotes.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface OssUploadService {

    ResponseResult uploadImg(MultipartFile img);
}
