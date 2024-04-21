package com.ankhnotes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FilePathUtils {
    /**
     * 根据文件名创建文件路径, 以 yyyy/MM/dd/uuid.原始后缀 的格式
     */
    public static String createFilePathByFileName(String fileName){
        //日期格式化输出, 利用日期作为文件路径
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");

        String datePath = simpleDateFormat.format(new Date());

        String uuidPath = UUID.randomUUID().toString();

        String fileType = fileName.substring(fileName.lastIndexOf("."));

        return datePath+uuidPath+fileType;
    }
}
