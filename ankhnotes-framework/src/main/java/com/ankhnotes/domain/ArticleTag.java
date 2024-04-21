package com.ankhnotes.domain;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2024-03-21 16:22:44
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_article_tag")
//文章-标签关联表实体类
/**
 * 双主键Mybatis-plus暂时无法设置, 先不设置主键
 * 启动类会出现 Warning: 没有设置主键, 可能Mybatis-plus一些需要主键的函数如getById()无法使用
 * save函数正常
 */
public class ArticleTag  {
    //文章id
    private Long articleId;
    //标签id
    private Long tagId;

}
