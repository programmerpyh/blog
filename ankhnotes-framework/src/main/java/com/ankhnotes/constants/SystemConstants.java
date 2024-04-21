package com.ankhnotes.constants;

public class SystemConstants {

    /**
     * 草稿文章, 即文章状态=1
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 已发布的文章, 即文章状态=0
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 查询最近文章的查询页数, 即分页后查询第几页=1
     */
    public static final int ARTICLE_STATUS_CURRENT = 1;

    /**
     * 查询最近文章的查询条数, 即分页后每页记录的条数=10
     */
    public static final int ARTICLE_STATUS_SIZE = 10;

    /**
     * 分类的状态, 0正常, 1禁用
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链的审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 根评论的上一级评论编号, 应当为-1
     * 即根评论的ToCommentId应当 = -1
     */

    public static final String COMMENT_ROOT = "-1";

    /**
     * type = 0 表示是文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * type = 1 表示是评论表中的友链评论类型
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 网站超级管理员Id
     */
    public static final Long Administrator_ID = 1L;

    /**
     * 七牛云accessKey
     */
    public static final String MYOSS_ACCESSKEY = "KsZ3xgUdaH6CJaWp94ScVyEcwqiUmTE-shvY3uC2";

    /**
     * 七牛云secretKey
     */
    public static final String MYOSS_SECRETKEY = "tgKmo3jrRcnRgKCDXDeYzbIRq2oDCGEzKLdNWk_Q";

    /**
     * 七牛云头像桶名
     */
    public static final String MYOSS_BUCKET = "ankhnotes-blog";

    /**
     * 七牛云CDN测试域名, 用于访问图片, 后面+key即可访问对应图片
     */
    public static final String OSS_URL = "http://sa6j1nmec.hn-bkt.clouddn.com/";

    /**
     * 权限表中的权限类型: 菜单类型
     */
    public static final String TYPE_MENU = "C";

    /**
     * 权限表中的权限类型:按钮类型
     */
    public static final String TYPE_BUTTON = "F";

    /**
     * 权限表中的权限类型:目录类型
     */
    public static final String TYPE_CATEGORY = "M";

    /**
     * 根菜单的id
     */
    public static final Long MENU_ROOT = 0L;

    /**
     * 判断用户type是否为管理员
     */
    public static final String IS_ADMIN = "1";
}
