package com.ankhnotes.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {

    private Long id;

    //标题
    private String title;

    //文章摘要
    private String summary;

    //文章内容
    private String content;

    private Long categoryId;

    private String categoryName;

    //访问量
    private Long viewCount;

    //是否允许评论 1是，0否
    private String isComment;

    private Date createTime;

    //缩略图
    private String thumbnail;

}
