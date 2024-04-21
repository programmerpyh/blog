package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 用于后台分页查询所有文章
 */
public class AdminArticleVO {

    //所属分类id
    private Long categoryId;

    //文章内容
    private String content;

    private Date createTime;

    private Long id;

    private String isComment;

    private String isTop;

    private String status;

    private String summary;

    private String thumbnail;

    private String title;

    private Long viewCount;

}
