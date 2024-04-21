package com.ankhnotes.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作为ArticleServiceImpl.hotArticleList()的响应类, 将所有查询出来的Article转化为HotArticleVO返回
 * 为了使得返回结果只返回所需字段, 直接返回Article会返回其他与该功能无关的字段
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVO {

    private Long id;

    private String title;

    private Long viewCount;

}
