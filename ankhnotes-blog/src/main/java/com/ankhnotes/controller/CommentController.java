package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.Comment;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 文章评论查询
     */
    @mySystemLog(logDescription = "文章评论查询")
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    /**
     * 友链评论查询
     */
    @mySystemLog(logDescription = "友链评论查询")
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }

    /**
     * 添加评论
     */
    @mySystemLog(logDescription = "添加评论")
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

}
