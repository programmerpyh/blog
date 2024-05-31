package com.ankhnotes.handler.mybatisplus;

import com.ankhnotes.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
//这个类是用来配置mybatis的字段自动填充。用于'发送评论'功能，由于我们在评论表无法对下面这四个字段进行插入数据(原因是前端在发送评论时，没有在
//请求体提供下面四个参数，所以后端在往数据库插入数据时，下面四个字段是空值)，所有就需要这个类来帮助我们往下面这四个字段自动的插入值，
//只要我们更新了评论表的字段，那么无法插入值的字段就自动有值了
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    /**
     * 配合@TableField的fill = FieldFill.INSERT, 可以实现在插入时自动更新此函数所设定的字段
     */
    public void insertFill(MetaObject metaObject) {

        Long loginUserId = null;
        try{
            loginUserId = SecurityUtils.getLoginUserId();
        }catch (Exception e){
            loginUserId = -1L;
        }

        //如果插入时间的"小时"不对, 则可能是时区错误, 需要设置mysql的url的serverTimezone=Asia/Shanghai
        Date now = new Date();

        this.setFieldValByName("createBy", loginUserId, metaObject);
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateBy", loginUserId, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
    }

    @Override
    /**
     * 配合@TableField的fill = FieldFill.UPDATE, 可以实现在更新操作时自动更新此函数所设定的字段
     */
    public void updateFill(MetaObject metaObject) {

        Long loginUserId = null;
        try{
            loginUserId = SecurityUtils.getLoginUserId();
        }catch (Exception e){
            //e.printStackTrace();
            loginUserId = -1L;
        }

        Date now = new Date();

        this.setFieldValByName("updateBy", loginUserId, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
    }
}
