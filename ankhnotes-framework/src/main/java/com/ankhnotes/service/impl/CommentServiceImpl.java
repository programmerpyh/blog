package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.Comment;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.mapper.CommentMapper;
import com.ankhnotes.mapper.UserMapper;
import com.ankhnotes.service.CommentService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.vo.CommentVO;
import com.ankhnotes.vo.PageVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-03-10 19:26:38
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询评论, 先查询根评论, 再查询子评论
     * 1. 文章编号一致(先判断是否为文章评论, 是则查询文章编号, 否则不需要这一步)
     * 2. 为根评论(RootId = -1)
     * 3. 分页
     * 4. VO按发布时间排倒序
     * 5. 根据CreateBy填充VO的userName
     * 6. 递归查询子评论, 填充VO的children
     * @param commentType 0表示是文章评论, 1表示友链评论
     * @param articleId 文章Id(若为友链评论查询则为null)
     * @param pageNum 分页页码
     * @param pageSize 分页大小
     */
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize){
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();

        commentWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        commentWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);
        commentWrapper.eq(Comment::getType, commentType);
        commentWrapper.orderByDesc(Comment::getCreateTime);

        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, commentWrapper);

        List<Comment> commentList = page.getRecords();

        List<CommentVO> commentVOS = BeanCopyUtils.copyBeanList(commentList, CommentVO.class);

        //处理commentVOS的username
        commentVOS.forEach(commentVO -> {
            commentVO.setUserName(userMapper.selectById(commentVO.getCreateBy()).getNickName());
        });

        //查询子评论
        checkChildCommentVOS(articleId, commentVOS);

        return ResponseResult.okResult(new PageVO<>(commentVOS, page.getTotal()));

    }

    /**
     * 递归查询子评论, 填充VO的children
     * @param articleId 查询的文章Id(若为友链评论查询则为null)
     * @param commentVOS 待查询子评论的评论列表
     */
    private void checkChildCommentVOS(Long articleId, List<CommentVO> commentVOS){
        for(CommentVO commentVO : commentVOS){
            LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();

            commentWrapper.eq(Objects.nonNull(articleId), Comment::getArticleId, articleId);
            commentWrapper.eq(Comment::getToCommentId, commentVO.getId());
            commentWrapper.orderByDesc(Comment::getCreateTime);

            List<Comment> childs = list(commentWrapper);

            if(!childs.isEmpty()) {

                List<CommentVO> childVOs = BeanCopyUtils.copyBeanList(childs, CommentVO.class);

                childVOs.forEach(childVO -> childVO.setUserName(userMapper.selectById(childVO.getCreateBy()).getNickName()));

                checkChildCommentVOS(articleId, childVOs);


                commentVO.setChildren(childVOs);
            }
        }
    }

    @Override
    public ResponseResult addComment(Comment comment) {

        if(!StringUtils.hasText(comment.getContent()))
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);

        save(comment);

        return ResponseResult.okResult();
    }
}
