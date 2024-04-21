package com.ankhnotes.domain;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2024-02-23 22:02:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_article")
public class Article  {
    @TableId
    //Redis里是String
    private Long id;

    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //所属分类名字, 用于ArticleListVO
    @TableField(exist = false) //表示表中不存在这个字段, 避免MybatisPlus扫描这个字段
    private String categoryName;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量(Redis里是Integer)
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    //设置了redis定时更新浏览量的定时更新任务, 由于定时更新时用户可能没有登录会导致异常抛出
    //@TableField(fill = FieldFill.INSERT)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    //设置了redis定时更新浏览量的定时更新任务, 由于定时更新时用户可能没有登录会导致异常抛出
    //@TableField(fill = FieldFill.INSERT)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    public Article(Long id, Long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}
