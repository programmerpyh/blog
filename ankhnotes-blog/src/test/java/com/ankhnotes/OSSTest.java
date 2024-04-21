package com.ankhnotes;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.*;

//@Component
@SpringBootTest
//@ConfigurationProperties(prefix = "myoss")
public class OSSTest {

    private String xxaccessKey = "KsZ3xgUdaH6CJaWp94ScVyEcwqiUmTE-shvY3uC2";
    private String xxsecretKey = "tgKmo3jrRcnRgKCDXDeYzbIRq2oDCGEzKLdNWk_Q";
    private String xxbucket = "ankhnotes-blog";

//    private String xxaccessKey;
//    private String xxsecretKey;
//    private String xxbucket;
//
//    public void setXxaccessKey(String xxaccessKey) {
//        this.xxaccessKey = xxaccessKey;
//    }
//
//    public void setXxsecretKey(String xxsecretKey) {
//        this.xxsecretKey = xxsecretKey;
//    }
//
//    public void setXxbucket(String xxbucket) {
//        this.xxbucket = xxbucket;
//    }

    @Test
    public void testOSS(){

        // OSS存储支持空间创建在不同的机房，在使用Java SDK中的UploadManager上传文件之前，必须要构建一个上传用的Configuration对象
        // 在该对象中，可以指定空间对应的Region以及其他的一些影响上传的参数。
        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传

        //由七牛云的开发文档复制而来↓
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //指定后会自动根据文件名在OSS里创建目录
        String key = "image/cat.png";
        try {
            InputStream file = new FileInputStream("D:\\资料\\java_learn\\project\\springbootblog\\ankhnotes-blog\\src\\main\\resources\\cat.png");
            Auth auth = Auth.create(xxaccessKey, xxsecretKey);
            String upToken = auth.uploadToken(xxbucket);
            try {

                Response response = uploadManager.put(file,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println("上传成功! 生成的key是: "+putRet.key);
                System.out.println("上传成功! 生成的hash是: "+putRet.hash);
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
        } catch (FileNotFoundException ex) {
            //ignore
        }
    }
}
