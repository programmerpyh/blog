package com.ankhnotes.service.impl;

import com.ankhnotes.domain.ArticleTag;
import com.ankhnotes.mapper.ArticleTagMapper;
import com.ankhnotes.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2024-03-21 16:22:44
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
