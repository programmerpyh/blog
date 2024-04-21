package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.service.OssUploadService;
import com.ankhnotes.utils.FilePathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class OssUploadServiceImpl implements OssUploadService {

    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        String originalFileName = img.getOriginalFilename();
        long fileSize = img.getSize();

        if(fileSize > 2*1024*1024)
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);

        assert originalFileName != null;
        if(!originalFileName.endsWith(".png") && !originalFileName.endsWith(".jpg"))
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);

        //上传七牛云
        String url = uploadOSS(img);

        return ResponseResult.okResult(url);
    }

    private String uploadOSS(MultipartFile img){
        String accessKey = SystemConstants.MYOSS_ACCESSKEY;
        String secretKey = SystemConstants.MYOSS_SECRETKEY;
        String bucket = SystemConstants.MYOSS_BUCKET;

        // OSS存储支持空间创建在不同的机房，在使用Java SDK中的UploadManager上传文件之前，必须要构建一个上传用的Configuration对象
        // 在该对象中，可以指定空间对应的Region以及其他的一些影响上传的参数。
        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.huanan());//OSS创建时为华南-广东
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传

        //由七牛云的开发文档复制而来↓
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //指定后会自动根据文件名在OSS里创建目录
        String key = FilePathUtils.createFilePathByFileName(Objects.requireNonNull(img.getOriginalFilename()));
        System.out.println("key = " + key);

        try {
            InputStream file = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {

                Response response = uploadManager.put(file,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println("上传成功! 生成的key是: "+putRet.key);
//                System.out.println("上传成功! 生成的hash是: "+putRet.hash);
                return SystemConstants.OSS_URL+key;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);
                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            //ignore
        }

        return "上传失败";
    }
}
