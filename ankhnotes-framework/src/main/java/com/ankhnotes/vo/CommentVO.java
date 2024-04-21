package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {

    //文章id
    private Long articleId;

    //评论内容
    private String content;

    private Long createBy;

    private Date createTime;

    private Long id;

    private Long rootId;

    //所回复的目标评论的userid
    private Long toCommentUserId;

    //所回复的目标评论的userName
    private String toCommentUserName;

    //回复目标评论id
    private Long toCommentId;

    //发这条评论的人的nickName(不在sg_comment表中)
    private String userName;

    //子评论VOs
    private List<CommentVO> children;

}
