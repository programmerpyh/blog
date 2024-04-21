package com.ankhnotes.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class WebUtils {

    /**
     * 将字符串渲染到客户端
     * @param response 响应操作体
     * @param string 待渲染的字符串 例如JSON.toJSONString(ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR))
     */
    public static void renderString(HttpServletResponse response, String string){//这里string表示JSON.toJSON()
        response.setStatus(200); //成功状态码
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置下载响应头(easyexcel文件响应头)
     * @param filename 文件名
     * @param response HttpServletResponse
     * @throws UnsupportedEncodingException 异常
     */
    public static void setDownloadHeader(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        //以附件形式下载
        response.setHeader("Content-disposition","attachment; filename="+fName);
    }

}
